package kameleon.model.auth;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne( fetch = FetchType.EAGER)
    @JoinColumn( name = "user_id")
    private User user;

    private Date expiryDate;

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public VerificationToken() {
        long HOUR = 3600*1000; // in milli-seconds.
        Date date = new Date();
        Date newDate = new Date(date.getTime() + EXPIRATION * HOUR);
        this.setExpiryDate(newDate);
    }

    public VerificationToken(String token, User user) {
        this.token= token;
        this.user = user;
        long HOUR = 3600*1000; // in milli-seconds.
        Date date = new Date();
        Date newDate = new Date(date.getTime() + EXPIRATION * HOUR);
        this.setExpiryDate(newDate);
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
