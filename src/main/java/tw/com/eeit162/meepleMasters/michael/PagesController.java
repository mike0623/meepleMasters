package tw.com.eeit162.meepleMasters.michael;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

	@GetMapping("/michael/index")
	public String processAction() {
		return "example/example";
	}
	
	
	@GetMapping("/michael/testPage")
	public String testPage() {
		return "michael/test";
	}
	
	@GetMapping("/michael/testWebsocket")
	public String testWebsocket(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("userEmail", request.getParameter("userEmail"));
		return "michael/michael";
	}
	
	@GetMapping("/michael/testIndex")
	public String testIndex() {
		return "michael/testIndex";
	}
	
	
	@GetMapping("/michael/friendList")
	public String friendList() {
		return "include/friend/friendList";
	}
	

}
