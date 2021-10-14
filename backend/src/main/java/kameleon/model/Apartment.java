package kameleon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Apartment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    private List<String> properties;
    @ElementCollection
    private List<String> pictures;
    @NotNull
    @Size(min=2, max=50)
    private String name;
    @NotNull
    @Size(min=5, max=100000)
    private String description;
    @NotNull
    private Integer price;



    public Apartment(){
        this.properties = null;
        this.name = null;
        this.pictures = null;
        this.price = null;
        this.description = null;
    }

    public Apartment(@JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("price") Integer price, @JsonProperty("properties") ArrayList<String> properties, @JsonProperty("pictures") ArrayList<String> pictures){
        this.properties = properties;
        this.pictures = pictures;
        this.price = price;
        this.description = description;
        this.name = name;
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

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
