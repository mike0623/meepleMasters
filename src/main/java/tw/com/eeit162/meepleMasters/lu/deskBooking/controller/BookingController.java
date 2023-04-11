package tw.com.eeit162.meepleMasters.lu.deskBooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import tw.com.eeit162.meepleMasters.lu.deskBooking.service.BookingService;



@Controller
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@GetMapping("/booking")
	public void processAction() {
		bookingService.insertbooking();
	}
}
