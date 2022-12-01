package kameleon.service;

import kameleon.dto.BookingDTO;
import kameleon.model.auth.OnRegistrationCompleteEvent;
import kameleon.model.auth.User;
import kameleon.model.booking.Booking;
import kameleon.model.booking.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MailService {

    @Value("${kameleon.app.backendURL}")
    private String backendURL;
    @Value("${kameleon.app.frontendURL}")
    private String frontendURL;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    public void sendRegisterVerificationEmail(String recipientAddress, String confirmationUrl ) {

        String subject = "E-mail-cím megerősítése";
        //String message = messages.getMessage("message.regSucc", null, event.getLocale());
        String link = backendURL + "/auth" + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("kameleonbalaton@gmail.com");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Köszönjük, hogy regisztráltál!\n \n A regisztrációt az alábbi linkre kattintva tudod érvényesíteni: \n"
                + link +"\n \n Üdvözlettel, \n Kaméleon apartmanház");
        mailSender.send(email);
    }

    public void sendEmail(String recipientAddress, String subject, String text ) {
        //String message = messages.getMessage("message.regSucc", null, event.getLocale());
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("kameleonbalaton@gmail.com");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(text);
        mailSender.send(email);
    }

    public void sendEmailForUserBookingCreated(String recipientAddress ) {

        String text = "Köszönjük a foglalást!\n \n Foglalásod sikeresen elmentettük, jelenleg feldolgozás alatt van. " +
                "A nyaraló tulajdonosa 1-2 napon belül visszajelez, erről emailben is értesíteni fogunk! " +
                "A foglalásod adatait a weboldalunkon keresztül tudod részletesen nyomon követni a bejelentkezést követően:\n"
                + frontendURL +"/#/user-booking \n \n Üdvözlettel, \n Kaméleon apartmanház";
        this.sendEmail(recipientAddress, "Sikeres foglalás", text);
    }

    public void sendEmailForAdminBookingCreated(User user ) {
        List<User> alladmin = userService.findAllAdmin();
        String text = "Kedves nyaralótulajdonos!\n \n Erősítsd meg "+ user.getLastName()+" "+ user.getFirstName() + " (" + user.getEmail() + ") " +" foglalását, hogy utána befizethesse a foglalót! Ha esetleg nem megfelelő az időpont, utasítsd el."
                + frontendURL+"/#/admin-booking \n \n Üdvözlettel, \n A Kaméleon apartmanház honlapja";
        for (User admin : alladmin){
            this.sendEmail(admin.getEmail(), "Új foglalás", text);
        }
    }


    public void sendEmailForUserBookingChanged(String recipientAddress, String firstname) {
        String text = "Kedves " + firstname + "!\n \n Foglalásod állapotát frissítette a nyaraló tulajdonosa. " +
                "A foglalásod adatait a weboldalunkon keresztül tudod részletesen nyomon követni a bejelentkezést követően:\n"
                + frontendURL +"/#/user-booking \n \n Üdvözlettel, \n Kaméleon apartmanház";
        this.sendEmail(recipientAddress, "Foglalásod állapota módosult", text);
    }

    public void sendEmailForAdminBookingChanged(User user) {
        List<User> alladmin = userService.findAllAdmin();
        String text = "Kedves nyaralótulajdonos!\n \n Sikeresen módosítottad "+ user.getLastName()+" "+ user.getFirstName() + " (" + user.getEmail() + ") " +" foglalását. A foglalás pontos adatait a weblapon keresztül nézheted meg: \n"
                + frontendURL+"/#/admin-booking \n \n Üdvözlettel, \n A Kaméleon apartmanház honlapja";
        for (User admin : alladmin){
            this.sendEmail(admin.getEmail(), "Módosított foglalás", text);
        }
    }

    public void sendEmailForUserBookingCanceled(String recipientAddress) {
        String text = "Foglalás lemondása\n \n Foglalásodat sikeresen lemondtad. Ha meggondolnád magad, bármikor újra foglalhatsz " +
                "apartmant weboldalunkon keresztül a bejelentkezést követően:\n"
                + frontendURL +"/#/booking \n \n Üdvözlettel, \n Kaméleon apartmanház";
        this.sendEmail(recipientAddress, "Foglalásod állapota módosult", text);
    }

    public void sendEmailForAdminBookingCanceled(User user) {
        List<User> alladmin = userService.findAllAdmin();
        String text = "Kedves nyaralótulajdonos!\n \n "+ user.getLastName()+" "+ user.getFirstName() + " (" + user.getEmail() + ") " +" lemondta egy foglalását. A jelenleg aktuális és lemondott foglalások listáját a weblapon keresztül nézheted meg: \n"
                + frontendURL+"/#/admin-booking \n \n Üdvözlettel, \n A Kaméleon apartmanház honlapja";
        for (User admin : alladmin){
            this.sendEmail(admin.getEmail(), "Lemondott foglalás", text);
        }
    }
}
