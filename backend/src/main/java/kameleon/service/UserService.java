package kameleon.service;

import exception.CustomMessageException;
import kameleon.dao.RoleRepository;
import kameleon.dao.UserRepository;
import kameleon.dto.UserDTO;
import kameleon.model.auth.Role;
import kameleon.model.auth.RoleName;
import kameleon.model.auth.User;
import kameleon.model.booking.Booking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static kameleon.model.auth.RoleName.ROLE_USER;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final BookingService bookingService;
    @Autowired
    private final AuthService authService;
    @Value("${kameleon.app.cloudinaryUploadPreset}")
    private String cloudinaryUploadPreset;
    @Value("${kameleon.app.cloudinaryName}")
    private String cloudinaryName;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BookingService bookingService, AuthService authService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bookingService = bookingService;
        this.authService = authService;
    }

    public Iterable<UserDTO> getUsers() {
        List<User> all = this.userRepository.findAll();
        List<UserDTO> allDTO = new ArrayList<UserDTO>();
        for (User user : all){
            allDTO.add(this.convertUser(user));
        }
        return allDTO;
    }

    
    public UserDTO convertUser(User user) {
        
        UserDTO dto = new UserDTO(user.getId(), user.getLastName(), user.getFirstName(), user.getEmail(), user.getPhonenumber(), user.hasAdminRole());
        return dto;
    }


    public UserDTO getUserById(long id) {
        User user = this.userRepository.findById(id).orElseThrow(() ->
                new CustomMessageException("Nincs ilyen felhasználó"));
        return convertUser(user);
    }

   /* public User updateUser(UserDTO newUser) {
        User oldUser = this.userRepository.findById(newUser.getId()).orElseThrow(() ->
                new CustomMessageException("Nincs ilyen felhasználó"));

        boolean newIsAdmin = newUser.getRoles().contains(RoleName.ROLE_ADMIN);
        Role oldRole = oldUser.getRoles().stream().filter(x -> x.getName().equals(RoleName.ROLE_ADMIN)).findFirst().orElse(null);

        if (!newIsAdmin && oldRole != null) {
            List<User> admins = userRepository.findAllAdmin(RoleName.ROLE_ADMIN);
            if (admins.size() == 1) {
                throw new CustomMessageApiException("Nem lehet módosítani az utolsó admint!");
            }
        }
        oldUser.setUsername(newUser.getUsername());
        oldUser.setName(newUser.getName());
        oldUser.setEmail(newUser.getEmail());

        Set<Role> newRoles = new HashSet<Role>();
        oldUser.clearRoles();

        newUser.getRoles().forEach(role -> {
            Role tempRole = roleRepository.findByName(role).orElseThrow(() ->
                    new CustomMessageApiException("Fail! -> Cause: User Role not found."));
            newRoles.add(tempRole);
//            for(Role r : newUser.getRoles()){
//                Role tmprole = this.roleRepository.findByName(r.getName()).orElseThrow(() -> {throw new RuntimeException("Nincs ilyen szerepkör");});
//                newRoles.add(tmprole);
//            }
        });
        oldUser.setRoles(newRoles);
        return this.userRepository.save(oldUser);
    }*/
   @Transactional
    public ResponseEntity<String> deleteUserById(long id) throws CustomMessageException{
        User user = userRepository.findById(id).orElseThrow(() -> new CustomMessageException("Nincs felhasználó ilyen id-val!"));
        Role isAdmin = user.getRoles().stream().filter(x -> x.getName().equals(RoleName.ROLE_ADMIN)).findFirst().orElse(null);

        if (isAdmin != null) {
            List<User> admins = this.findAllAdmin();
            if (admins.size() == 1) {
                return new ResponseEntity<>("Nem lehet kitörölni az utolsó admint!",
                        HttpStatus.BAD_REQUEST);
            }
        }
       //delete bookings
       try {
           bookingService.deleteBookingsOfUser(user);
       }
       catch(CustomMessageException e){
           return new ResponseEntity<>("Nem lehet kitörölni a felhasználót, mert rendelkezik aktív foglalással.",
                   HttpStatus.BAD_REQUEST);
       }
       //delete its verificationtoken
       authService.deleteVerificationToken(user);
       //delete user role
       user.setRoles(new HashSet<>());
       userRepository.saveAndFlush(user);
       //delete user
       userRepository.delete(user);
       return new ResponseEntity<>("Felhasználó sikeresen törölve",
               HttpStatus.OK);
    }

    public ResponseEntity<String> changeUserRole(long id) throws CustomMessageException{
        User user = userRepository.findById(id).orElseThrow(() -> new CustomMessageException("Nincs felhasználó ilyen id-val!"));
        Set<Role> roles =user.getRoles();
        Role isAdmin = roles.stream().filter(x -> x.getName().equals(RoleName.ROLE_ADMIN)).findFirst().orElse(null);

        UserDTO current = this.getCurrentUser();
        if(current.getId()== user.getId()){
            return new ResponseEntity<>("Nem tudod törölni saját magad admin jogosultságát!",
                        HttpStatus.BAD_REQUEST);
        }
        //ha admin a felhasznalo
        if (isAdmin != null) {
            List<User> admins = this.findAllAdmin();
            if (admins.size() == 1) {
                return new ResponseEntity<>("Nem lehet kitörölni az utolsó admint!",
                        HttpStatus.BAD_REQUEST);
            } else{
                Set<Role> newroles = new HashSet<>();
                Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find. Run POST request /api/auth/setup/roles first!"));
                newroles.add(userRole);
                user.setRoles(newroles);
                userRepository.save(user);
                return new ResponseEntity<>("Felhasználó admin jogosultága sikeresen törölve", HttpStatus.OK);
            }
        } else{
            Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Admin Role not find. Run POST request /api/auth/setup/roles first!"));
            roles.add(adminRole);
            user.setRoles(roles);
            userRepository.save(user);
            return new ResponseEntity<>("Felhasználó admin jogosultága sikeresen hozzáadva",
            HttpStatus.OK);
        }
    }

    

    public UserDTO getUserByEmail(String email) {
        User user = getFullUserByEmail(email);
        return convertUser(user);
    }

    public User getFullUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() ->
                new CustomMessageException("Nincs ilyen felhasználó"));
        return user;
    }



    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    public UserDTO getCurrentUser() {
        return convertUser(authService.getCurrentFullUser());
    }

    public void updateUser(UserDTO user) {
        User u = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new RuntimeException("No user has been found with given email"));
        u.setFirstName(user.getFirstname());
        u.setLastName(user.getLastname());
        u.setPhonenumber(user.getPhonenumber());

        userRepository.save(u);
    }

    public void setupRoles() {
        roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        roleRepository.save(new Role(ROLE_USER));
        return;
    }

    public void setupAdmin(String password) {
        //leellenőrzöm hogy létre lett-e már hozva ilyen felhasználó
        if (userRepository.existsByEmail("admin@kameleon.hu")) {
            throw new RuntimeException("Fail -> Email is already taken!");
        }

        //hozzáadom az admint
        User user = new User("Kameleon","Admin", "kameleonbalaton@gmail.com","kameleonbalaton@gmail.com", "+36303699697",
                authService.encodePassword(password), new ArrayList<Booking>());
        user.setEnabled(true);

        Set<Role> roles = new HashSet<>();
        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find. Run POST request /api/auth/setup/roles first!"));
        roles.add(adminRole);
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find. Run POST request /api/auth/setup/roles first!"));
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        return;
    }

    public void setupTestUser(String password, String email) {
        if (userRepository.existsByEmail("admin@kameleon.hu")) {
            throw new RuntimeException("Fail -> Email is already taken!");
        }

        User user = new User("","", email,email, "",
                authService.encodePassword(password), new ArrayList<Booking>());
        user.setEnabled(true);

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find. Run POST request /api/auth/setup/roles first!"));
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        return;
    }

    public List<User> findAllAdmin() {
        return  userRepository.findAllAdmin(RoleName.ROLE_ADMIN);
    }

    public List<String> sendCloudinaryCredentials(){
        List<String> cloudinary = new ArrayList<String>();
        cloudinary.add(this.cloudinaryName);
        cloudinary.add(this.cloudinaryUploadPreset);
        return cloudinary;
    }
}

