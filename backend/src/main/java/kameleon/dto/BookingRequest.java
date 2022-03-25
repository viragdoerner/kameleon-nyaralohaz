package kameleon.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.NotNull;
import kameleon.model.apartman.Apartment;
import kameleon.model.auth.User;
import kameleon.model.booking.BookingStatus;
import kameleon.model.booking.StatusTransition;
import serializer.CustomApartmentSerializer;
import serializer.CustomUserSerializer;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class BookingRequest {

    @NotNull
    private Boolean dogIncluded;

    @NotNull
    private Date arrival;

    @NotNull
    private Date departure;

    @NotNull
    private Long apartmentId;

    private String comment;

    public BookingRequest() {
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

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

