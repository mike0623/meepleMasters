package tw.com.eeit162.meepleMasters.lu.deskBooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.lu.deskBooking.model.BookingBean;
import tw.com.eeit162.meepleMasters.lu.deskBooking.model.BookingDao;

@Service
public class BookingService {
	
	@Autowired
	private BookingDao bookingDao;
	
	public void insertbooking() {
		BookingBean booking = new BookingBean();
		booking.setBookTime("上午");
		
		
		
		bookingDao.save(booking);
		
	}
}
