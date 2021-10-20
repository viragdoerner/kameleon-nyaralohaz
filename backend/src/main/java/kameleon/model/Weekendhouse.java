package kameleon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import javax.validation.constraints.Size;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Weekendhouse {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    private List<String> properties;
    @NotNull
    @Size(min=2, max=50)
    private String address;
    @NotNull
    @Size(min=15, max=50)
    private String phone_number;
    @NotNull
    @Size(min=2, max=50)
    private String email;
    @NotNull
    @Size(min=2, max=1000000)
    private String description;



    public Weekendhouse(){
        this.properties = null;
        this.address = null;
        this.phone_number = null;
        this.email = null;
        this.description = null;
    }

    public Weekendhouse(@JsonProperty("address") String address, @JsonProperty("phone_number") String phone_number, @JsonProperty("email") String email, @JsonProperty("description") String description, @JsonProperty("properties") ArrayList<String> properties){
        this.properties = properties;
        this.address = address;
        this.phone_number = phone_number;
        this.email = email;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
