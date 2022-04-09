package kameleon.dto;

import kameleon.model.booking.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingListsDTO {

    private List<Booking> active;

    private List<Booking> inactive;

    public BookingListsDTO() {
        this.active = new ArrayList<Booking>();
        this.inactive = new ArrayList<Booking>();
    }

    public List<Booking> getActive() {
        return active;
    }

    public void setActive(List<Booking> active) {
        this.active = active;
    }

    public void addActive(Booking b) {
        this.active.add(b);
    }

    public void addInactive(Booking b) {
        this.inactive.add(b);
    }

    public List<Booking> getInactive() {
        return inactive;
    }

    public void setInactive(List<Booking> inactive) {
        this.inactive = inactive;
    }
}
