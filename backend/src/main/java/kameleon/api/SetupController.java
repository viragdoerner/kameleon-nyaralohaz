package kameleon.api;

import kameleon.dao.RoleRepository;
import kameleon.dao.UserRepository;
import kameleon.model.apartman.Apartment;
import kameleon.model.apartman.Weekendhouse;
import kameleon.service.ApartmentService;
import kameleon.service.UserService;
import kameleon.service.WeekendhouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.security.access.annotation.Secured;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/setup")
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

    @Secured("ROLE_ADMIN")
    @PostMapping("/weekendhouse")
    public ResponseEntity<?> setupWeekendhouse(@RequestBody Weekendhouse wh) {

        weekendhouseService.setupWh(wh);

        return new ResponseEntity<>("Weekendhouse was succesfully added!",
                HttpStatus.OK);
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/apartment")
    public ResponseEntity<?> setupApartments(@RequestBody List<Apartment> apartments) {

       apartmentService.setupApartments(apartments);

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
    public ResponseEntity<?> setupAdmin(@RequestParam("password") String password) {

        userService.setupAdmin(password);


        return new ResponseEntity<>("Admin user succesfully added!",
                HttpStatus.OK);
    }

    @PostMapping("/testuser")
    public ResponseEntity<?> setupAdmin(@RequestParam("password") String password,@RequestParam("email") String email) {

        userService.setupTestUser(password, email);


        return new ResponseEntity<>("Test user succesfully added!",
                HttpStatus.OK);
    }

}
