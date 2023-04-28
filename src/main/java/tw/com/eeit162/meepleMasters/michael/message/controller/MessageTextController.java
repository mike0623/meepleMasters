package tw.com.eeit162.meepleMasters.michael.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.michael.message.service.MessageTextService;

@Controller
public class MessageTextController {
	
	@Autowired
	private MessageTextService textService;
	
	@GetMapping("/messageText/findById/{id}")
	@ResponseBody
	public String findById(@PathVariable("id") Integer id) {
		return textService.findById(id);
	}
	
	@PostMapping("/messageText/insertMessageText")
	@ResponseBody
	public Integer insertMessageText(@RequestBody String text) {
		Integer insertMessageText = textService.insertMessageText(text);
		return insertMessageText;
	}
}
