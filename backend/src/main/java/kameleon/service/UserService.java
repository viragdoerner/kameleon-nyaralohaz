package kameleon.service;

import exception.CustomMessageException;
import kameleon.dao.RoleRepository;
import kameleon.dao.UserRepository;
import kameleon.dto.UserDTO;
import kameleon.model.Role;
import kameleon.model.RoleName;
import kameleon.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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
        UserDTO dto = new UserDTO();
        dto.setFirstname(user.getFirstName());
        dto.setLastname(user.getLastName());
        dto.setEmail(user.getUsername());
        dto.setPhonenumber(user.getPhonenumber());
        dto.setId(user.getId());
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
        User user = this.userRepository.findByEmail(email).orElseThrow(() ->
                new CustomMessageException("Nincs ilyen felhasználó"));
        return convertUser(user);
    }
}
