package kameleon.api;

import exception.CustomMessageException;
import kameleon.dao.RoleRepository;
import kameleon.dao.UserRepository;
import kameleon.dao.VerificationTokenRepository;
import kameleon.dto.JwtResponse;
import kameleon.dto.LoginForm;
import kameleon.dto.RegisterForm;
import kameleon.model.auth.OnRegistrationCompleteEvent;
import kameleon.model.auth.Role;
import kameleon.model.auth.RoleName;
import kameleon.model.auth.User;
import kameleon.model.auth.VerificationToken;
import kameleon.model.booking.Booking;
import kameleon.service.AuthService;
import kameleon.service.UserService;
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
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new CustomMessageException(
                        "No user found with username: " + loginRequest.getEmail()));
        if (!user.isEnabled()){
            throw new CustomMessageException(
                    "This user account isn't verified yet.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterForm registerRequest,
                                          HttpServletRequest request, Errors errors) {

        boolean taken = userRepository.existsByEmail(registerRequest.getEmail());
        if (taken) {
            return new ResponseEntity<>("Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }
        User user = new User("", "", registerRequest.getEmail(), registerRequest.getEmail(), "",
                encoder.encode(registerRequest.getPassword()), new ArrayList<Booking>());
        try{
            // Creating user's account

            Set<Role> roles = new HashSet<>();

            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
            roles.add(userRole);
            user.setRoles(roles);

            user = userRepository.save(user);

            String appUrl = request.getContextPath();
            OnRegistrationCompleteEvent event = new OnRegistrationCompleteEvent(user, appUrl);
            eventPublisher.publishEvent(event);
        } catch (RuntimeException e) {
           return this.handleEmailSendFailure(user);
        }
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }
    ResponseEntity<String> handleEmailSendFailure(User user) {
        try {
            VerificationToken token = verificationTokenRepository.findByUser(user);
            verificationTokenRepository.deleteById(token.getId());
            user.setRoles(new HashSet<>());
            userRepository.save(user);
            userRepository.delete(user);
            return new ResponseEntity<>("Emailerror: Wrong email address, user was removed", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Emailerror:  Wrong email address, couldn't delete user", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration
            ( @RequestParam("token") String token) {

        //Locale locale = request.getLocale();

        VerificationToken verificationToken = authService.getVerificationToken(token);
        if (verificationToken == null) {
            //String message = messages.getMessage("auth.message.invalidToken", null, locale);
            //model.addAttribute("message", "Invalid token");
            //return "redirect:/badUser.html?lang=" + locale.getLanguage();
            return "redirect:/badUser.html";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            //String messageValue = messages.getMessage("auth.message.expired", null, locale)
            //model.addAttribute("message", "Expired token");
            //return "redirect:/badUser.html?lang=" + locale.getLanguage();
            return "redirect:/badUser.html";
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return "Sikeres regisztráció!" ;
    }

}
