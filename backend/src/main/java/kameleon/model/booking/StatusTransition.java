package kameleon.model.booking;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import kameleon.model.apartman.Apartment;
import kameleon.model.apartman.ApartmentProperty;
import kameleon.model.auth.User;

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
    private BookingStatus newStatus;

    @NotNull
    private BookingStatus oldStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Booking booking;

    public StatusTransition() {
    }

    public StatusTransition(@JsonProperty("comment") String comment, @JsonProperty("created") Date created,
                   @JsonProperty("newStatus") BookingStatus newStatus, @JsonProperty("oldStatus") BookingStatus oldStatus,
                   @JsonProperty("booking") Booking booking ){
        this.comment = comment;
        this.created = created;
        this.newStatus = newStatus;
        this.booking = booking;
        this.oldStatus = oldStatus;
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

    public BookingStatus getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(BookingStatus oldStatus) {
        this.oldStatus = oldStatus;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
