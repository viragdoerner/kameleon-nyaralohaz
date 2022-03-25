package kameleon.service;

import kameleon.dao.BookingRepository;
import kameleon.dao.StatusTransitionRepository;
import kameleon.model.booking.Booking;
import kameleon.model.booking.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private BookingRepository bookingRepository;
    private StatusTransitionRepository transitionRepository;

    @Autowired
    BookingService(BookingRepository br, StatusTransitionRepository tr){
        this.bookingRepository = br;
        this.transitionRepository = tr;
    }

    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }
/*
    public List<Booking> getAllBookingFromUser() {
    }

    public Booking getActiveBookingFromUser() {
    }*/

    public Booking bookApartment(Booking booking) {
        //hibát dobni ha van más aktív foglalása a usernek
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        /*boolean hasActiveBooking = this.bookingRepository.existsByStatus(BookingStatus.TENTATIVE) ;
        if(hasActiveBooking){

        }*/
        //tentative típusú foglalás létrehozása
        //email admin
        //email user
        return new Booking();
    }
}
