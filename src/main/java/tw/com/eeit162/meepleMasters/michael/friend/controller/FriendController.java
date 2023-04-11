package tw.com.eeit162.meepleMasters.michael.friend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import tw.com.eeit162.meepleMasters.michael.friend.service.FriendService;

@Controller
public class FriendController {
	@Autowired
	private FriendService friendService;

	@GetMapping("/friend/")
	public void processAction() {
		friendService.insertFriend();
	}
	
}
