package kameleon.dao;

import kameleon.model.booking.Booking;
import kameleon.model.booking.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("Select b From Booking b join b.user user where user.username = :username")
    List<Booking> findAllOwnedByUsername(String username);
}
