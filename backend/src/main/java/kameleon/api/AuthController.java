package kameleon.api;

import kameleon.dao.RoleRepository;
import kameleon.dao.UserRepository;
import kameleon.dto.JwtResponse;
import kameleon.dto.LoginForm;
import kameleon.model.Role;
import kameleon.model.RoleName;
import kameleon.model.User;
import kameleon.service.ApartmentService;
import kameleon.service.FileStorageService;
import kameleon.service.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/setup/roles")
    public ResponseEntity<?> setupRoles() {

        //hozzáadom a használt szerepeket
        roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        roleRepository.save(new Role(RoleName.ROLE_USER));

        return new ResponseEntity<>("Admin and user roles successfully added!",
                HttpStatus.OK);
    }
    @PostMapping("/setup/admin")
    public ResponseEntity<?> setupAdmin() {

        //leellenőrzöm hogy létre lett-e már hozva ilyen felhasználó
        if (userRepository.existsByUsername("sbt-admin")) {
            return new ResponseEntity<>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        //hozzáadom az admint
        User user = new User("Kameleon Admin", "admin", "admin@kameleon.hu",
                encoder.encode("kameleonadminpassword"));

        Set<Role> roles = new HashSet<>();
        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find. Run POST request /api/auth/setup/roles first!"));
        roles.add(adminRole);
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find. Run POST request /api/auth/setup/roles first!"));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<>("Admin user succesfully added!",
                HttpStatus.OK);
    }

}
