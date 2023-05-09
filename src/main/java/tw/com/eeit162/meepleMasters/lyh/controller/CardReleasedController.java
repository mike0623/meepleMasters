package tw.com.eeit162.meepleMasters.lyh.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.lyh.model.bean.Card;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardOwned;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardReleased;
import tw.com.eeit162.meepleMasters.lyh.service.CardReleasedService;

@Controller
@RequestMapping(path = {"/released"})
public class CardReleasedController {

	@Autowired
	private CardReleasedService cRService;
	
	@GetMapping("/ownedCard")
	public ResponseEntity<?> showMyCardList(HttpSession session) {
		
		Member member = (Member)session.getAttribute("member");
		Integer memberId = member.getMemberId();
		
		List<CardOwned> ownedCard = cRService.showOwnedCard(memberId);
//		System.out.println(ownedCard.toString());
		
		JSONObject jsonObject = new JSONObject();
		JSONArray dataArray = new JSONArray();
		
		for (CardOwned card: ownedCard) {
			
			if (card.getCardStatus() == 1) {
				dataArray.put(card);
			}
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
	
	
	@PostMapping("/insertCardDirect")
	public String insertCardReleased(@RequestParam("ownedId") Integer ownedId, @RequestParam("price") Integer price, @RequestParam("endTime") String endTime) throws ParseException {
		
		Date date = null;

		date = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
		
		cRService.insertCardReleasedDirect(ownedId, price, date);
		
		return "lyh/cardReleased";
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> showAllRelease() {
		
		List<CardReleased> allReleaseCard = cRService.showAllReleased();
		
		
		JSONObject jsonObject = new JSONObject();
		JSONArray dataArray = new JSONArray();
		
		for (CardReleased card: allReleaseCard) {
			if (card.getReleasedStatus() == 1) {
				dataArray.put(card);
			}
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
	
	@GetMapping("/my")
	public ResponseEntity<?> showMyRelease(HttpSession session) {
		
		Member member = (Member)session.getAttribute("member");
		Integer memberId = member.getMemberId();
		
		List<CardReleased> allReleaseCard = cRService.showAllReleased();
		
		JSONObject jsonObject = new JSONObject();
		JSONArray dataArray = new JSONArray();
		
		for (CardReleased card: allReleaseCard) {
			if (card.getReleasedStatus() == 1 && card.getFkOwnedId().equals(memberId)) {
				dataArray.put(card);
			}
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
	
	
	
	
	
}
