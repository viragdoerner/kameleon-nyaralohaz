package kameleon.model.booking;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.NotNull;
import kameleon.model.apartman.Apartment;
import kameleon.model.apartman.Weekendhouse;
import kameleon.model.auth.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

   // @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonSerialize(using = CustomUserSerializer.class)
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @NotNull
    private Boolean dogIncluded;

    @NotNull
    private Date arrival;

    @NotNull
    private Date departure;

    //@JsonBackReference
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonSerialize(using = CustomApartmentSerializer.class)
    private Apartment apartment;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<StatusTransition> transitions;

    public Booking() {
    }

    public Booking(@JsonProperty("user") User user, @JsonProperty("status") BookingStatus status,
                   @JsonProperty("dogIncluded") Boolean dogIncluded, @JsonProperty("apartment") Apartment apartment,
                   @JsonProperty("transitions") List<StatusTransition> transitions ){
        this.user = user;
        this.status = status;
        this.dogIncluded = dogIncluded;
        this.arrival = arrival;
        this.departure = departure;
        this.apartment = apartment;
        this.transitions = transitions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Boolean getDogIncluded() {
        return dogIncluded;
    }

    public void setDogIncluded(Boolean dogIncluded) {
        this.dogIncluded = dogIncluded;
    }

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public List<StatusTransition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<StatusTransition> transitions) {
        this.transitions = transitions;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}

