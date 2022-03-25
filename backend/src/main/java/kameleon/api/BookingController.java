package kameleon.api;

import exception.CustomMessageException;
import kameleon.model.apartman.Apartment;
import kameleon.model.booking.Booking;
import kameleon.service.ApartmentService;
import kameleon.service.BookingService;
import kameleon.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/booking")
@RestController
public class BookingController {


        private final BookingService bookingService;

        @Autowired
        public BookingController(BookingService bookingService) {
            this.bookingService = bookingService;
        }
        @Secured("ROLE_ADMIN")
        @GetMapping
        public List<Booking> getAllBooking() {
            return bookingService.getAllBooking();
        }

        /*@Secured("ROLE_USER")
        @GetMapping(path = "/user")
        public List<Booking> getAllBookingFromUser() {
            List<Booking> a =bookingService.getAllBookingFromUser();
            return a;
        }

        @Secured("ROLE_USER")
        @GetMapping(path = "/user/active")
        public Booking getActiveBookingFromUser() {
            Booking a =bookingService.getActiveBookingFromUser();
            return a;
        }*/

        @Secured("ROLE_USER")
        @PostMapping
        public Booking bookApartment(@RequestBody Booking booking) {
            Booking a =bookingService.bookApartment(booking);
            return a;
        }
    }

