package tw.com.eeit162.meepleMasters.lu.deskBooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Optional<BookingBean> findBookingById(int bookingId) {
        
    	return bookingDao.findById(bookingId);
    }

    public List<BookingBean> findAllBookings() {
        return bookingDao.findAll();
    }
	

    public void updateBooking(BookingBean booking) {
        bookingDao.save(booking);
    }

    public void deleteBookingById(int bookingId) {
        bookingDao.deleteById(bookingId);
    }
    
}
