package tw.com.eeit162.meepleMasters.lu.deskBooking.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDao extends JpaRepository<BookingBean, Integer> {

}
