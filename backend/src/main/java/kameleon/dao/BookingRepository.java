package kameleon.dao;

import kameleon.model.booking.Booking;
import kameleon.model.booking.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Boolean existsByStatus(BookingStatus status);
}
