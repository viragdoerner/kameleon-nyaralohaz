package kameleon.dao;


import kameleon.model.booking.Booking;
import kameleon.model.booking.StatusTransition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusTransitionRepository extends JpaRepository<StatusTransition, Long>{
}
