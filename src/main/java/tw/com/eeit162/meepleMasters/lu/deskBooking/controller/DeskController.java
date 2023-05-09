package tw.com.eeit162.meepleMasters.lu.deskBooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tw.com.eeit162.meepleMasters.lu.deskBooking.model.DeskBean;
import tw.com.eeit162.meepleMasters.lu.deskBooking.service.DeskService;

@Controller
public class DeskController {

	@Autowired
	private DeskService deskService;

	@GetMapping("/desk")
	public String getDeskList(Model model) {
		model.addAttribute("desks", deskService.findAllDesks());
		model.addAttribute("deskBean", new DeskBean());
		return "desklist";
	}
}
