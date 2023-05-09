package tw.com.eeit162.meepleMasters.lyh.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.lyh.model.bean.Card;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardOwned;
import tw.com.eeit162.meepleMasters.lyh.service.CardReleasedService;

@Controller
@RequestMapping(path = {"/released"})
public class CardReleasedController {

	@Autowired
	private CardReleasedService cRService;
	
	@GetMapping("/ownedCard")
	public ResponseEntity<?> showAllCardList(HttpSession session) {
		
		Member member = (Member)session.getAttribute("member");
		Integer memberId = member.getMemberId();
		
		List<CardOwned> ownedCard = cRService.showOwnedCard(memberId);
//		System.out.println(ownedCard.toString());
		
		JSONObject jsonObject = new JSONObject();
		JSONArray dataArray = new JSONArray();
		
		for (CardOwned card: ownedCard) {
			dataArray.put(card);
//			System.out.println(card.toString());
		}
		
		jsonObject.put("cardList", dataArray);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		if (dataArray != null && !dataArray.isEmpty()) {
			return new ResponseEntity<>(jsonObject.toMap(), headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/getCard/{cardId}")
	public ResponseEntity<?> showCardInfo(@PathVariable(name = "cardId") Integer cardId) {
		
		
		Card card = cRService.findCardById(cardId);
		
		JSONObject jsonObject = new JSONObject();
		JSONArray dataArray = new JSONArray();
		
		jsonObject.put("card", card);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		if (jsonObject != null && !jsonObject.isEmpty()) {
			return new ResponseEntity<>(jsonObject.toMap(), headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@PostMapping("/insertCardReleased")
	public String insertCardReleased(@RequestParam("123") Integer memberId) {
		
		
		cRService.insertCardReleased(null, null, null);
		
		return "";
	}
	
	@GetMapping("/all")
	public String showAllRelease() {
		cRService.showAllReleased();
		return "";
	}
	
	
	
	
}
