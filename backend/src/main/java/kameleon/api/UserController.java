package kameleon.api;

import kameleon.dto.UserDTO;
import kameleon.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

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
        return this.userService.getUsers();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path = "{id}")
    public UserDTO getUserById(@PathVariable("id") long id){
        return this.userService.getUserById(id);
    }

    @Secured("ROLE_USER")
    @GetMapping(path = "/current")
    public UserDTO getCurrentUser(){
        return this.userService.getCurrentUser();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path = "{email}")
    public UserDTO getUserByEmail(@PathVariable("email") String email){
        return this.userService.getUserByEmail(email);
    }

    @Secured("ROLE_USER")
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDTO user){
        try {
            this.userService.updateUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>("Data update failed: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User info successfuly updated",
                HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @Transactional
    @DeleteMapping ("{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") long id){
        return this.userService.deleteUserById(id);

    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/cloudinary")
    public Iterable<String> sendCloudinaryCredentials(){
        return this.userService.sendCloudinaryCredentials();
    }
}

