package tw.com.eeit162.meepleMasters.michael.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.michael.message.service.StickerService;

@Controller
public class StickerController {
	
	@Autowired
	private StickerService stickerService;
	
	@GetMapping("/sticker/getSticker/{stickerId}")
	@ResponseBody
	public ResponseEntity<byte[]> getSticker(@PathVariable("stickerId") Integer stickerId) {
		byte[] sticker = stickerService.findStickerById(stickerId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		
		return new ResponseEntity<byte[]>(sticker,headers,HttpStatus.OK);
	}
}
