package kameleon.api;

import kameleon.dto.UserDTO;
import kameleon.model.User;
import kameleon.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public Iterable<UserDTO> getAllUsers(){
        System.out.println("hellooo");
        return this.userService.getUsers();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path = "{id}")
    public UserDTO getUserById(@PathVariable("id") long id){
        return this.userService.getUserById(id);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path = "{email}")
    public UserDTO getUserByEmail(@PathVariable("email") String email){
        return this.userService.getUserByEmail(email);
    }

   /* @Secured("ROLE_ADMIN")
    @PatchMapping
    public User updateUser(@RequestBody UserDTO user){
        return this.userService.updateUser(user);
    }*/

    @Secured("ROLE_ADMIN")
    @Transactional
    @DeleteMapping ("{id}")
    public void deleteUserById(@PathVariable("id") long id){
        this.userService.deleteUserById(id);
    }
}

