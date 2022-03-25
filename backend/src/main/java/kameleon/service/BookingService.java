package kameleon.service;

import exception.CustomMessageException;
import jdk.net.SocketFlow;
import kameleon.dao.BookingRepository;
import kameleon.dao.StatusTransitionRepository;
import kameleon.dto.BookingRequest;
import kameleon.model.apartman.Apartment;
import kameleon.model.auth.User;
import kameleon.model.booking.Booking;
import kameleon.model.booking.BookingStatus;
import kameleon.model.booking.StatusTransition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    private BookingRepository bookingRepository;
    private UserService userService;
    private ApartmentService apartmentService;
    private StatusTransitionRepository transitionRepository;

    @Autowired
    BookingService(BookingRepository br, StatusTransitionRepository tr, UserService us, ApartmentService as){
        this.bookingRepository = br;
        this.transitionRepository = tr;
        this.userService = us;
        this.apartmentService = as;
    }

    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }
/*
    public List<Booking> getAllBookingFromUser() {
    }

    public Booking getActiveBookingFromUser() {
    }*/

    public Booking bookApartment(BookingRequest bookingRequest) {
        //hibát dobni ha van más aktív foglalása a usernek
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = getCurrentUsername();
        if(currentUsername == null){
            throw new CustomMessageException("Nincs bejelentkezett felhasználó!");
        }
        if(hasActiveBooking(currentUsername)){
            throw new CustomMessageException("Már van érvényes foglalásod! Egyszerre csak egy foglalás lehetséges.");
        }

        //tentative típusú foglalás létrehozása
        Booking booking = createBookingFromRequest(bookingRequest);
        bookingRepository.save(booking);

        //TODO email admin
        //TODO email user
        return booking;
    }

    private Booking createBookingFromRequest(BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setDogIncluded(bookingRequest.getDogIncluded());
        booking.setArrival(bookingRequest.getArrival());
        booking.setDeparture(bookingRequest.getDeparture());
        booking.setStatus(BookingStatus.TENTATIVE);

        StatusTransition transition = new StatusTransition();
        transition.setComment(bookingRequest.getComment());
        transition.setCreated(new Date());
        transition.setNewStatus(BookingStatus.TENTATIVE);
        booking.addTransition(transition);

        Apartment a = apartmentService.getApartmentById(bookingRequest.getApartmentId());
        if(a == null ){
            throw new CustomMessageException("Nincs ilyen apartman");
        }
        booking.setApartment(a);
        User u = userService.getFullUserByEmail(getCurrentUsername());
        booking.setUser(u);

        return booking;
    }

    boolean hasActiveBooking(String username){
        List<Booking> bookings = bookingRepository.findAllOwnedByUsername(username);
        boolean hasActiveBooking = false;
        for(Booking b : bookings){
            if(b.getStatus() != BookingStatus.DELETED){
                hasActiveBooking = true;
            }
        }
        return hasActiveBooking;
    }

    String getCurrentUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        return null;
    }
}
