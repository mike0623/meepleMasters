package tw.com.eeit162.meepleMasters.jack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteController {
	
	@GetMapping({"/index"})
	public String index() {
		
		return "include/index";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "jack/loginPage";
	}
	
	@GetMapping("/register")
	public String register() {
		
		return "jack/createMember"; 
	}
	
	@GetMapping("/member/admin")
	public String adminPage() {
		
		return "include/admin";
	}
	
	@GetMapping("/member/member")
	public String memberPage() {
		
		return "jack/memberPage";
	}
	
	@GetMapping("/member/profile")
	public String profile() {
		
		return "jack/profile";
	}

}	
