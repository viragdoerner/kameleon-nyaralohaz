package kameleon.dto;

import org.hibernate.annotations.NaturalId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDTO {

    private Long id;

    @NotBlank
    @Size(min=3, max = 50)
    private String lastname;

    @NotBlank
    @Size(min=3, max = 50)
    private String firstname;

    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NaturalId
    @NotBlank
    @Size(max = 50)
    private String phonenumber;

    public UserDTO() {
    }

    public UserDTO(Long id, String lastname, String firstname, String email, String phonenumber) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}

