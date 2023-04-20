package tw.com.eeit162.meepleMasters.lyh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.eeit162.meepleMasters.lyh.model.bean.Card;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardOwned;
import tw.com.eeit162.meepleMasters.lyh.service.CardListService;

@RestController
@RequestMapping(path = {"/card"})
public class CardListController {
	
	@Autowired
	private CardListService cListService;
	
	@PostMapping("/getNewCard/{memberId}")
	public String getNewCard(@PathVariable(name = "memberId") Integer memberId) {
		
		CardOwned newCard = cListService.insertCardToList(memberId);
		
		if (newCard != null) {
			return newCard.toString();
		}
		
		return "抽卡失敗";
	}
	
	@GetMapping("/list/{memberId}")
	public String listOwnedCard(@PathVariable(name = "memberId") Integer memberId) {
		
		List<CardOwned> list = cListService.listOwnedCard(memberId);
		
		if (list != null && !list.isEmpty()) {
			return list.toString();
		}
		
		return "沒有卡片";
	}

	@GetMapping("/downloadCard/{cardId}")
	public ResponseEntity<byte[]> downloadCard(@PathVariable(name = "cardId") Integer cardId) {
		
		Card card = cListService.findById(cardId);
		
		byte[] cardFile = card.getCardImg();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		return new ResponseEntity<byte[]>(cardFile, headers, HttpStatus.CREATED);
		
	}

}
