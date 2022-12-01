package kameleon.api;

import exception.CustomMessageException;
import kameleon.dto.BookingDTO;
import kameleon.dto.BookingListsDTO;
import kameleon.dto.BookingRequest;
import kameleon.dto.BookingStatusChangeRequest;
import kameleon.service.BookingService;
import kameleon.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/booking")
@RestController
public class BookingController {


    private final BookingService bookingService;
    private final MailService mailService;

        @Autowired
        public BookingController(BookingService bookingService, MailService mailService) {
            this.bookingService = bookingService;
            this.mailService = mailService;
        }

        @Secured("ROLE_ADMIN")
        @GetMapping
        public BookingListsDTO getAllBooking() {
            return bookingService.getAllBooking();
        }

        @Secured("ROLE_USER")
        @GetMapping(path = "/user")
        public BookingListsDTO getAllBookingFromUser() {
            BookingListsDTO b = bookingService.getBookingListsFromUser();
            return b;
        }

        @Secured("ROLE_USER")
        @PostMapping
        public BookingDTO bookApartment(@RequestBody BookingRequest booking) {
            BookingDTO b =bookingService.bookApartment(booking);
            mailService.sendEmailForUserBookingCreated(b.getUser().getEmail());
            mailService.sendEmailForAdminBookingCreated(b.getUser());
            return b;
        }

        @Secured("ROLE_ADMIN")
        @PutMapping(path = "/{booking_id}")
        public BookingDTO changeBookingStatus(@PathVariable("booking_id") Long booking_id, @RequestBody BookingStatusChangeRequest request) {
            BookingDTO b =bookingService.changeBookingStatus(booking_id, request);
            mailService.sendEmailForUserBookingChanged(b.getUser().getEmail(), b.getUser().getFirstName());
            mailService.sendEmailForAdminBookingChanged(b.getUser());
            return b;
        }

        @Secured("ROLE_USER")
        @PutMapping(path = "/cancel/{booking_id}")
        public BookingDTO cancelBooking(@PathVariable("booking_id") Long booking_id, @RequestBody BookingStatusChangeRequest request) {
            BookingDTO b =bookingService.cancelBooking(request, booking_id);
            mailService.sendEmailForUserBookingCanceled(b.getUser().getEmail());
            mailService.sendEmailForAdminBookingCanceled(b.getUser());
            return b;
        }

        @Secured("ROLE_ADMIN")
        @DeleteMapping(path = "/{booking_id}")
        public ResponseEntity<?> deleteBooking(@PathVariable("booking_id") Long booking_id) {
            try {
                bookingService.deleteBooking(booking_id);
            } catch (CustomMessageException e) {
                return new ResponseEntity<>(e.getMessage(),
                        HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Sikeres törlés",
                    HttpStatus.OK);
        }


        @GetMapping(path = "/disabled_dates/{apartment_id}/{dogIncluded}")
        public List<String> getDisabledDates(@PathVariable("apartment_id") Long apartment_id,@PathVariable("dogIncluded") Boolean dogIncluded) {
            List<String> disabled_dates = bookingService.getDisabledDates(apartment_id, dogIncluded);
            return disabled_dates;
        }
    }

