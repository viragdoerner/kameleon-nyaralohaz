package kameleon.api;

import exception.CustomMessageException;
import kameleon.dao.RoleRepository;
import kameleon.dao.UserRepository;
import kameleon.dto.JwtResponse;
import kameleon.dto.LoginForm;
import kameleon.dto.RegisterForm;
import kameleon.model.apartman.Property;
import kameleon.model.apartman.Weekendhouse;
import kameleon.model.auth.OnRegistrationCompleteEvent;
import kameleon.model.auth.Role;
import kameleon.model.auth.RoleName;
import kameleon.model.auth.User;
import kameleon.model.auth.VerificationToken;
import kameleon.model.booking.Booking;
import kameleon.service.ApartmentService;
import kameleon.service.UserService;
import kameleon.service.WeekendhouseService;
import kameleon.service.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

import static kameleon.model.auth.RoleName.ROLE_USER;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class SetupController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    WeekendhouseService weekendhouseService;
    @Autowired
    ApartmentService apartmentService;


    @PostMapping("/weekendhouse")
    public ResponseEntity<?> setupWeekendhouse() {

        weekendhouseService.setupWh();

        return new ResponseEntity<>("Weekendhouse was succesfully added!",
                HttpStatus.OK);
    }
    @PostMapping("/apartment")
    public ResponseEntity<?> setupApartments() {

       apartmentService.setupApartments();

        return new ResponseEntity<>("Apartments were succesfully added!",
                HttpStatus.OK);
    }
    @PostMapping("/roles")
    public ResponseEntity<?> setupRoles() {
        //hozzáadom a használt szerepeket
        userService.setupRoles();
        return new ResponseEntity<>("Admin and user roles successfully added!",
                HttpStatus.OK);
    }
    @PostMapping("/admin")
    public ResponseEntity<?> setupAdmin() {

        userService.setupAdmin();


        return new ResponseEntity<>("Admin user succesfully added!",
                HttpStatus.OK);
    }

}
