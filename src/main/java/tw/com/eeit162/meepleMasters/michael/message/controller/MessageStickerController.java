package tw.com.eeit162.meepleMasters.michael.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.michael.message.service.MessageStickerService;

@Controller
public class MessageStickerController {
	@Autowired
	private MessageStickerService stickerService;
	
	@PostMapping("/messageSticker/insertMessageSticker")
	@ResponseBody
	public Integer insertMessageSticker(@RequestBody Integer messageSticker) {
		return stickerService.insertMessageSticker(messageSticker);
	}
	
	
	@GetMapping("/messageSticker/findById/{id}")
	@ResponseBody
	public Integer findById(@PathVariable("id") Integer id) {
		return stickerService.findById(id);
	}

	//測試join
//	@PostMapping("/t/t")
//	@ResponseBody
//	public void tt(@RequestBody String json) {
//		stickerService.findStickerByxxx(json);
//	}
}
