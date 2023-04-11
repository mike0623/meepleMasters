package tw.com.eeit162.meepleMasters.lu.deskBooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.lu.deskBooking.model.DeskBean;
import tw.com.eeit162.meepleMasters.lu.deskBooking.model.DeskDao;

@Service
public class DeskService {
	
	@Autowired
	private DeskDao bookingDao;
	
	public void insertdeak() {
		DeskBean desk = new DeskBean();
		desk.setDeskNumber(1);
		desk.setDeskPrice(200);
		desk.setDeskType("2人桌");
		
		
		bookingDao.save(desk);
		
	}
}