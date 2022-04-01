package kameleon.dto;

import com.sun.istack.NotNull;
import kameleon.model.booking.BookingStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

public class BookingStatusChangeRequest {

    @Enumerated(EnumType.STRING)
    private BookingStatus newStatus;

    @Size(min=0, max=100000000)
    private String comment;

    public BookingStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(BookingStatus newStatus) {
        this.newStatus = newStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
