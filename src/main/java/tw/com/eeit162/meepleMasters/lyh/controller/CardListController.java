package tw.com.eeit162.meepleMasters.lyh.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.lyh.model.bean.Card;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardOwned;
import tw.com.eeit162.meepleMasters.lyh.service.CardListService;

@Controller
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
	
	@GetMapping("/mycard/{memberId}")
	public String listOwnedCard(@PathVariable(name = "memberId") Integer memberId, Model model) {
		
		List<CardOwned> list = cListService.listOwnedCard(memberId);
		
		ArrayList<Card> cardList = new ArrayList<>();

		ArrayList<Card> starOrderList = new ArrayList<>();
		
		if (list != null && !list.isEmpty()) {
			
			for (int i=0; i<list.size();i++) {
//				cardId.add(list.get(i).getFkCardId());
				cardList.add(cListService.findById(list.get(i).getFkCardId()));
			}
			
			for (Card card: cardList) {
				if (card.getCardStar() == 5) {
					starOrderList.add(card);
				} 
			}
			for (Card card: cardList) {
				if (card.getCardStar() == 4) {
					starOrderList.add(card);
				} 
			}
			for (Card card: cardList) {
				if (card.getCardStar() == 3) {
					starOrderList.add(card);
				} 
			}
			for (Card card: cardList) {
				if (card.getCardStar() == 2) {
					starOrderList.add(card);
				} 
			}
			for (Card card: cardList) {
				if (card.getCardStar() == 1) {
					starOrderList.add(card);
				} 
			}
//			model.addAttribute("cardId", cardId);
			model.addAttribute("cardList", starOrderList);
			
			return "lyh/mycard";
		}
		
		return null;
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
