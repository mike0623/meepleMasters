package tw.com.eeit162.meepleMasters.lyh.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.lyh.model.bean.Card;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardOwned;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardReleased;
import tw.com.eeit162.meepleMasters.lyh.model.dto.CardDto;
import tw.com.eeit162.meepleMasters.lyh.service.CardReleasedService;

@Controller
@RequestMapping(path = { "/released" })
public class CardReleasedController {

	@Autowired
	private CardReleasedService cRService;

	@GetMapping("/ownedCard")
	public ResponseEntity<?> showMyCardList(HttpSession session) {

		Member member = (Member) session.getAttribute("member");
		Integer memberId = member.getMemberId();

		List<CardOwned> ownedCard = cRService.showMyCard(memberId);
//		System.out.println(ownedCard.toString());

		JSONObject jsonObject = new JSONObject();
		JSONArray dataArray = new JSONArray();

		for (CardOwned card : ownedCard) {

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
	public String insertCardReleased(@RequestParam("ownedId") Integer ownedId, @RequestParam("price") Integer price,
			@RequestParam("endTime") String endTime) throws ParseException {

		Date date = null;

		date = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);

		Date newDate = cal.getTime();

		cRService.insertCardReleasedDirect(ownedId, price, newDate);

		return "lyh/cardReleased";
	}

	@GetMapping("/TestAAA")
	@ResponseBody
	public ArrayList<CardDto> Test() {

		// 取得所有card
		List<CardReleased> allReleaseCard = cRService.showAllReleased();
		// 過濾掉下架的card
		List<CardReleased> listedCards = allReleaseCard.stream().filter(card -> card.getReleasedStatus() == 1)
				.collect(Collectors.toList());

		ArrayList<CardDto> cDList = new ArrayList<CardDto>();

		listedCards.stream().forEach(cardReleased -> {

			CardOwned cardOwned = cRService.showOnwedDetail(cardReleased.getFkOwnedId());
			Card card = cRService.findCardById(cardOwned.getFkCardId());

			CardDto cardDto = new CardDto();

			cardDto.setReleasedId(cardReleased.getReleasedId());
			cardDto.setOwnedId(cardOwned.getOwnedId());
			cardDto.setDirectPrice(cardReleased.getDirectPrice());
			cardDto.setStartPrice(cardReleased.getStartPrice());
			cardDto.setType(cardReleased.getType());
			cardDto.setEndTime(cardReleased.getEndTime());
			cardDto.setReleasedStatus(cardReleased.getReleasedStatus());
			cardDto.setCardId(card.getCardId());
			cardDto.setMemberId(cardOwned.getFkMemberId());
			cardDto.setCardStatus(cardOwned.getCardStatus());
			cardDto.setCardName(card.getCardName());
			cardDto.setCardStar(card.getCardStar());
			
			cDList.add(cardDto);
		});

		cDList.sort((a,b)->b.getDirectPrice()-a.getDirectPrice());
		
		
		return cDList;
	}

	
	
	@GetMapping("/all")
	public ResponseEntity<?> showAllRelease() {

		List<CardReleased> allReleaseCard = cRService.showAllReleased();

		JSONObject jsonObject = new JSONObject();
		JSONArray dataArray = new JSONArray();

		for (CardReleased card : allReleaseCard) {
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

		Member member = (Member) session.getAttribute("member");
		Integer memberId = member.getMemberId();

//		System.out.println("memberId " + memberId);

		List<CardReleased> allReleaseCard = cRService.showMyReleased(memberId);

		JSONObject jsonObject = new JSONObject();
		JSONArray dataArray = new JSONArray();

		for (CardReleased card : allReleaseCard) {
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

	@GetMapping("/owned/{ownedId}")
	public ResponseEntity<?> showOwnedCard(@PathVariable("ownedId") Integer ownedId) {
		CardOwned showOnwedDetail = cRService.showOnwedDetail(ownedId);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("showOnwedDetail", showOnwedDetail);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		if (jsonObject != null && !jsonObject.isEmpty()) {
			return new ResponseEntity<>(jsonObject.toMap(), headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

}
