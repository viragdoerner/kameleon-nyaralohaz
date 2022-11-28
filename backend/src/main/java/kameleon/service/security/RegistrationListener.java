package kameleon.service.security;

import kameleon.model.auth.OnRegistrationCompleteEvent;
import kameleon.model.auth.User;
import kameleon.service.AuthService;
import kameleon.service.MailService;
import kameleon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private AuthService authService;

    @Autowired
    private MailService mailService;

    @Value("${kameleon.app.backendURL}")
    private String backendURL;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        authService.createVerificationToken(user, token);

        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm?token=" + token;
        mailService.sendRegisterVerificationEmail(user.getEmail(), confirmationUrl );
    }
}
