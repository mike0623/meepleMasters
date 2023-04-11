package tw.com.eeit162.meepleMasters.lu.deskBooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import tw.com.eeit162.meepleMasters.lu.deskBooking.service.DeskService;

@Controller
public class DeskController {
	
	@Autowired
	private DeskService deskService;
	
	@GetMapping("/desk")
	public void processAction() {
		deskService.insertdeak();
	}
	
}
