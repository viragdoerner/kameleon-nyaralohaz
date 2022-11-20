package kameleon.model.auth;

import kameleon.service.AuthService;
import kameleon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        authService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "E-mail-cím megerősítése";
        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm?token=" + token;
        //String message = messages.getMessage("message.regSucc", null, event.getLocale());
        String link = "http://localhost:8080/auth" + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("kameleonnyaralohaz@gmail.com");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Köszönjük, hogy regisztráltál!\n \n A regisztrációt az alábbi linkre kattintva tudod érvényesíteni: \n" + link +"\n \n Üdvözlettel, \n Kaméleon nyaralóház");
        mailSender.send(email);
    }
}
