package tw.com.eeit162.meepleMasters.michael.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import tw.com.eeit162.meepleMasters.michael.message.service.FriendMessageService;

@Controller
public class FriendMessageController {
	
	@Autowired
	private FriendMessageService messageService;
	

	@GetMapping("/friendMessage/")
	public void processAction() {
		messageService.insertFriendMessage();
	}
	
}
