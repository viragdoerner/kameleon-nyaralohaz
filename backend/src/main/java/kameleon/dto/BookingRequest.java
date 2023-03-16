package kameleon.dto;

import com.sun.istack.NotNull;
import java.util.Date;

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

