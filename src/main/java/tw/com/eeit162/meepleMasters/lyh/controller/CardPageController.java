package tw.com.eeit162.meepleMasters.lyh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CardPageController {

	@GetMapping("/cardList")
	public String cardList() {
		return "lyh/cardList";
	}
	
	@GetMapping("/mycard")
	public String mycard() {
		return "lyh/mycard";
	}
	
	@GetMapping("/card/releasedList")
	public String cardReleased() {
		return "lyh/cardReleased";
	}
	
	@GetMapping("/newRelease")
	public String newCardReleased() {
		return "lyh/newCardRelease";
	}
	
	@GetMapping("/editRelease")
	public String editCardReleased() {
		return "lyh/editCardRelease";
	}
	
	@GetMapping("/newAuction")
	public String newCardAuction() {
		return "lyh/newCardAuction";
	}
	
}
