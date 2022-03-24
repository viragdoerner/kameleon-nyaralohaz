package kameleon.dao;


import kameleon.model.apartman.Weekendhouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekendhouseRepository extends JpaRepository<Weekendhouse, Long> {
}
