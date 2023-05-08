package tw.com.eeit162.meepleMasters.lyh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.com.eeit162.meepleMasters.lyh.service.CardReleasedService;

@Controller
@RequestMapping(path = {"/released"})
public class CardReleasedController {

	@Autowired
	private CardReleasedService cRService;
	
	@GetMapping("/new")
//	public String
	
	
	@PostMapping("/insertCardReleased")
	public String insertCardReleased(@RequestParam("123") Integer memberId) {
		
		
		cRService.insertCardReleased(null, null, null);
		
		return "";
	}
	
	@GetMapping("/all")
	public String showAllRelease() {
		cRService.showAllReleased();
		return "";
	}
	
	
	
	
}
