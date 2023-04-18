package tw.com.eeit162.meepleMasters.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping("/header")
	public String header() {
		return "include/header";
	}

	@GetMapping("/footer")
	public String footer() {
		return "include/footer";
	}
	
	@GetMapping("/")
	public String home() {
		return "include/index";
	}
	
}
