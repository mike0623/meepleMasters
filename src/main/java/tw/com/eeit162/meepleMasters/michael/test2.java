package tw.com.eeit162.meepleMasters.michael;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class test2 {

	@GetMapping("michael/index")
	public String processAction() {
		return "example/example";
	}

}
