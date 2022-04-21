package kameleon.dto;

import kameleon.model.booking.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingListsDTO {

    private List<BookingDTO> active;

    private List<BookingDTO> inactive;

    public BookingListsDTO() {
        this.active = new ArrayList<BookingDTO>();
        this.inactive = new ArrayList<BookingDTO>();
    }

    public List<BookingDTO> getActive() {
        return active;
    }

    public void setActive(List<BookingDTO> active) {
        this.active = active;
    }

    public void addActive(BookingDTO b) {
        this.active.add(b);
    }

    public void addInactive(BookingDTO b) {
        this.inactive.add(b);
    }

    public List<BookingDTO> getInactive() {
        return inactive;
    }

    public void setInactive(List<BookingDTO> inactive) {
        this.inactive = inactive;
    }
}
