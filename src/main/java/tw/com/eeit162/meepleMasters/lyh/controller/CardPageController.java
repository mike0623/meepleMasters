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
	
	
}
