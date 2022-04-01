package kameleon.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.NotNull;
import kameleon.model.booking.Booking;
import kameleon.model.booking.BookingStatus;
import kameleon.model.booking.StatusTransition;
import serializer.CustomTransitionSerializer;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

public class StatusTransitionDTO {

    private Long id;

    private String comment;

    private Date created;

    private BookingStatus newStatus;


    public StatusTransitionDTO() {
    }
    public StatusTransitionDTO(StatusTransition tr) {
        this.comment = tr.getComment();
        this.newStatus = tr.getNewStatus();
        this.created = tr.getCreated();
        this.id=tr.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public BookingStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(BookingStatus newStatus) {
        this.newStatus = newStatus;
    }
}
