package kameleon.service;

import exception.CustomMessageException;
import kameleon.dao.BookingRepository;
import kameleon.dao.StatusTransitionRepository;
import kameleon.dto.BookingRequest;
import kameleon.dto.BookingStatusChangeRequest;
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

    public Booking bookApartment(BookingRequest bookingRequest) {
        //hibát dobni ha van más aktív foglalása a usernek
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(hasActiveBooking()){
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

    boolean hasActiveBooking(){
        Booking activeBooking = getActiveBookingFromUser();
        return activeBooking != null;
    }

    String getCurrentUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        throw new CustomMessageException("Nincs bejelentkezett felhasználó!");
    }

    public List<Booking> getAllBookingFromUser() {
        return bookingRepository.findAllOwnedByUsername(getCurrentUsername());
    }

    public Booking getActiveBookingFromUser() {
        List<Booking> bookings = getAllBookingFromUser();
        for(Booking b : bookings){
            if(b.getStatus() != BookingStatus.DELETED && b.getStatus() != BookingStatus.OUTDATED){
                return b;
            }
        }
        return null;
    }

    public Booking changeBookingStatus(Long booking_id, BookingStatusChangeRequest request) {
        //státusz módosíása
        //statustransition létrehozása
        Booking b = bookingRepository.findById(booking_id).orElseThrow(() ->new CustomMessageException("Nem létezik foglalás ezzel az ID-val"));
        if(b.getStatus() == request.getNewStatus()){
            return b;
        }
        b.setStatus(request.getNewStatus());
        StatusTransition transition = new StatusTransition(request, b);
        b.addTransition(transition);
        bookingRepository.save(b);
        transitionRepository.save(transition);
        //TODO emailek küldése
        return b;
    }

    public Booking cancelBooking(BookingStatusChangeRequest request) {
        Booking b = getActiveBookingFromUser();
        if(b.getStatus() == request.getNewStatus()){
            return b;
        }
        if(b == null ){
            throw new CustomMessageException("Nincs aktív foglalás");
        }
        request.setNewStatus(BookingStatus.DELETED);
        b.setStatus(BookingStatus.DELETED);
        StatusTransition transition = new StatusTransition(request, b);
        b.addTransition(transition);
        bookingRepository.save(b);
        transitionRepository.save(transition);
        //TODO emailek küldése
        return b;
    }

    public void deleteBooking(Long booking_id) {
        Booking b = bookingRepository.findById(booking_id).orElseThrow(() ->new CustomMessageException("Nem létezik foglalás ezzel az ID-val"));
        bookingRepository.deleteById(booking_id);
        //TODO email küldése??
    }
}
