package tw.com.eeit162.meepleMasters.lu.deskBooking.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingDao extends JpaRepository<BookingBean, Integer> {

	List<BookingBean> findByBookMemberId(Integer memberId);
	
	List<BookingBean> findByBookDateAndBookTimeAndBookDeskId(Date bookDate, String bookTime, Integer bookDeskId);

	List<BookingBean> findByBookDateAndBookTime(Date bookDate, String bookTime);

	List<BookingBean> findByBookDate(Date bookDate);
	
}
