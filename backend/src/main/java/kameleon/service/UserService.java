package kameleon.service;

import exception.CustomMessageException;
import kameleon.dao.RoleRepository;
import kameleon.dao.UserRepository;
import kameleon.dao.VerificationTokenRepository;
import kameleon.dto.UserDTO;
import kameleon.model.auth.Role;
import kameleon.model.auth.RoleName;
import kameleon.model.auth.User;
import kameleon.model.auth.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
        UserDTO dto = new UserDTO(user.getId(), user.getLastName(), user.getFirstName(), user.getEmail(), user.getPhonenumber());
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

    public void deleteUserById(long id) throws CustomMessageException{
        User user = userRepository.findById(id).orElseThrow(() -> new CustomMessageException("Nincs felhasználó ilyen id-val!"));
        Role isAdmin = user.getRoles().stream().filter(x -> x.getName().equals(RoleName.ROLE_ADMIN)).findFirst().orElse(null);

        if (isAdmin != null) {
            List<User> admins = userRepository.findAllAdmin(RoleName.ROLE_ADMIN);
            if (admins.size() == 1) {
                throw new CustomMessageException("Nem lehet kitörölni az utolsó admint!");
            }
        }

        user.setRoles(null);
        this.userRepository.saveAndFlush(user);
        this.userRepository.deleteById(id);
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

    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    public UserDTO getCurrentUser() {
        return convertUser(getCurrentFullUser());
    }

    public User getCurrentFullUser() {
        String currentUsername = getCurrentUsername();
        User user = userRepository.findByEmail(currentUsername).orElseThrow(() -> new RuntimeException("No user has been found with given email"));
        return user;
    }

    String getCurrentUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        throw new CustomMessageException("Nincs bejelentkezett felhasználó!");
    }

    public void updateUser(UserDTO user) {
        User u = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new RuntimeException("No user has been found with given email"));
        u.setFirstName(user.getFirstname());
        u.setLastName(user.getLastname());
        u.setPhonenumber(user.getPhonenumber());

        userRepository.save(u);
    }
}

