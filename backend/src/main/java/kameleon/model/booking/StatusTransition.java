package kameleon.model.booking;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.NotNull;
import kameleon.dto.BookingStatusChangeRequest;
import kameleon.model.apartman.Apartment;
import kameleon.model.apartman.ApartmentProperty;
import kameleon.model.auth.User;
import serializer.CustomApartmentSerializer;
import serializer.CustomTransitionSerializer;
import serializer.CustomUserSerializer;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class StatusTransition {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(columnDefinition = "LONGTEXT")
    @Size(min=2, max=100000000)
    private String comment;

    @NotNull
    private Date created;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BookingStatus newStatus;

    @JsonBackReference
    @JsonSerialize(using = CustomTransitionSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonSerialize(using = CustomUserSerializer.class)
    private User editor;

    public StatusTransition() {
    }
    public StatusTransition(BookingStatusChangeRequest request, Booking b, User editor) {
        this.comment = request.getComment();
        this.newStatus = request.getNewStatus();
        this.created = new Date();
        this.booking = b;
        this.editor = editor;
    }

    public StatusTransition(@JsonProperty("comment") String comment, @JsonProperty("created") Date created,
                   @JsonProperty("newStatus") BookingStatus newStatus,
                            @JsonProperty("booking") Booking booking, @JsonProperty("editor") User editor ){
        this.comment = comment;
        this.created = created;
        this.newStatus = newStatus;
        this.booking = booking;
        this.editor = editor;
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

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }
}
