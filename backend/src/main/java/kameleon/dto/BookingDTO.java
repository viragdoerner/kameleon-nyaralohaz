package kameleon.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.NotNull;
import kameleon.model.apartman.Apartment;
import kameleon.model.auth.User;
import kameleon.model.booking.Booking;
import kameleon.model.booking.BookingStatus;
import kameleon.model.booking.StatusTransition;
import serializer.CustomApartmentSerializer;
import serializer.CustomUserSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static kameleon.service.BookingService.getDatesBetweenUsingJava9;

public class BookingDTO {

    private Long id;

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

    @NotNull
    @JsonSerialize(using = CustomApartmentSerializer.class)
    private Apartment apartment;

    private List<StatusTransition> transitions;

    private int totalCost;

    private boolean active;

    public BookingDTO() {
    }

    public BookingDTO(Booking b) {
        this.id = b.getId();
        this.user = b.getUser();
        this.status = b.getStatus();
        this.dogIncluded = b.getDogIncluded();
        this.arrival = b.getArrival();
        this.departure = b.getDeparture();
        this.apartment = b.getApartment();
        this.transitions = b.getTransitions();
        this.totalCost = countTotalCost(b);
        this.active = checkIfActive(b);
    }

    private boolean checkIfActive(Booking b) {
        BookingStatus status = b.getStatus();
        if(status == BookingStatus.DELETED || status == BookingStatus.OUTDATED){
            return false;
        }
        return true;
    }

    private int countTotalCost(Booking b) {
        List<LocalDate> days = getDatesBetweenUsingJava9(b.getArrival(), incrementDateByOneDay(b.getDeparture()));
        return days.size();
    }

    private Date incrementDateByOneDay(Date d) {
        Date dayAfter = new Date(d.getTime() + TimeUnit.DAYS.toMillis( 1 ));
        return dayAfter;
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

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public List<StatusTransition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<StatusTransition> transitions) {
        this.transitions = transitions;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}


