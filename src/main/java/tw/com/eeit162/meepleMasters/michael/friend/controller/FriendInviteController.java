package tw.com.eeit162.meepleMasters.michael.friend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import tw.com.eeit162.meepleMasters.michael.friend.service.FriendInviteService;

@Controller
public class FriendInviteController {
	@Autowired
	private FriendInviteService inviteService;
	
	@GetMapping("/friend/friendInvite")
	public void processAction() {
		inviteService.insertFriendInvite();
	}
	
}
