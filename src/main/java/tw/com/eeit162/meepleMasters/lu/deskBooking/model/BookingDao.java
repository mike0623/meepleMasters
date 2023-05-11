package tw.com.eeit162.meepleMasters.lu.deskBooking.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDao extends JpaRepository<BookingBean, Integer> {

	List<BookingBean> findByBookMemberId(Integer memberId);
}
