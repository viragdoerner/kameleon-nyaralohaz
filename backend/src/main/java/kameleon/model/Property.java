package kameleon.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Property {
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
    private Weekendhouse weekendhouse;

    public Property(){
        this.name = null;
        this.icon_name = null;
        this.weekendhouse = null;
    }

    public Property(@JsonProperty("name") String name, @JsonProperty("icon_name") String icon_name, @JsonProperty("weekendhouse") Weekendhouse weekendhouse){

        this.icon_name = icon_name;
        this.name = name;
        this.weekendhouse = weekendhouse;
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

    public Weekendhouse getWeekendhouse() {
        return weekendhouse;
    }

    public void setWeekendhouse(Weekendhouse weekendhouse) {
        this.weekendhouse = weekendhouse;
    }
}

