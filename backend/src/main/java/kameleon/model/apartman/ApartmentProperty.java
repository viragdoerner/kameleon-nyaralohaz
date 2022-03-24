package kameleon.model.apartman;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table
public class ApartmentProperty {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min=2, max=50)
    private String name;
    @NotNull
    @Size(min=5, max=20)
    private String icon_name;
    @JsonBackReference
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Apartment apartment;

    public ApartmentProperty(){
        this.name = null;
        this.icon_name = null;
        this.apartment = null;
    }

    public ApartmentProperty(@JsonProperty("name") String name, @JsonProperty("icon_name") String icon_name, @JsonProperty("apartment") Apartment apartment){

        this.icon_name = icon_name;
        this.name = name;
        this.apartment = apartment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon_name() {
        return icon_name;
    }

    public void setIcon_name(String icon_name) {
        this.icon_name = icon_name;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}

