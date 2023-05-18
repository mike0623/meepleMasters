package tw.com.eeit162.meepleMasters.lu.deskBooking.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.lu.deskBooking.model.BookingBean;
import tw.com.eeit162.meepleMasters.lu.deskBooking.model.BookingDao;
import tw.com.eeit162.meepleMasters.lu.deskBooking.model.BookingDto;
import tw.com.eeit162.meepleMasters.lu.deskBooking.model.DeskBean;

@Service
@Transactional
public class BookingService {

	@Autowired
	private BookingDao bookingDao;

	public BookingBean convertToBookingBean(BookingDto bookingDto) {
		BookingBean bookingBean = new BookingBean();
		// 設定其他屬性
		return bookingBean;
	}

	public BookingBean insertBooking(BookingBean booking) {
		return bookingDao.save(booking);
	}

	public int getBookingId() {
		return this.getBookingId();
	}

	public List<BookingBean> findAllBookingsByMemberId(Integer memberId) {
		return bookingDao.findByBookMemberId(memberId);
	}

	public BookingBean findBookingById(int bookingId) {

		return bookingDao.findById(bookingId).orElse(null);
	}

	public List<BookingBean> findAllBookings() {
		return bookingDao.findAll();
	}

	@Transactional
	public BookingBean updateBookingById(BookingBean booking) {
		Optional<BookingBean> ob = bookingDao.findById(booking.getBookId());
		if (ob.isPresent()) {
			BookingBean Booking = ob.get();
			Booking.setBookDeskId(booking.getBookDeskId());
			Booking.setBookDate(booking.getBookDate());
			Booking.setBookTime(booking.getBookTime());
			bookingDao.save(Booking);

		} else {
			throw new RuntimeException("Booking not found with ID: " + booking.getBookId());
		}
		return booking;
	}
	
	
    public List<BookingBean> findByBookDateAndBookTime(Date bookDate, String bookTime) {
        return bookingDao.findByBookDateAndBookTime(bookDate, bookTime);
    }
    public List<BookingBean> findByBookDate(Date bookDate) {
        return bookingDao.findByBookDate(bookDate);
    }
    
    public Map<String, Integer> checkAvailability(Date bookDate) {
        List<BookingBean> bookings = bookingDao.findByBookDate(bookDate);
        Map<String, Integer> availabilityMap = new HashMap<>();
        
        availabilityMap.put("morning", 0);
        availabilityMap.put("afternoon", 0);
        availabilityMap.put("night", 0);

        String key = "";
        for (BookingBean booking : bookings) {
            String bookTime = booking.getBookTime();
            if("早上".equals(bookTime)) {
            	key = "morning";
            }
            if("中午".equals(bookTime)) {
            	key = "afternoon";
            }
            if("晚上".equals(bookTime)) {
            	key = "night";
            }
            int count = availabilityMap.getOrDefault(key, 0);
            availabilityMap.put(key, count + 1);
        }
        System.out.println(availabilityMap);

        return availabilityMap;
    }


	public void deleteBookingById(int bookingId) {
		bookingDao.deleteById(bookingId);
	}

}
