package tw.com.eeit162.meepleMasters.lyh.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jack.service.MemberService;
import tw.com.eeit162.meepleMasters.lyh.model.bean.Card;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardOwned;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardReleased;
import tw.com.eeit162.meepleMasters.lyh.model.dto.CardReleasedDto;
import tw.com.eeit162.meepleMasters.lyh.service.CardListService;
import tw.com.eeit162.meepleMasters.lyh.service.CardReleasedService;

@Controller
@RequestMapping(path = { "/released" })
public class CardReleasedController {

	@Autowired
	private CardListService cListService;

	@Autowired
	private CardReleasedService cRService;

	@Autowired
	private MemberService mService;

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

		return "redirect:/card/releasedList";
	}

	@PostMapping("/insertCardAuction")
	public String insertCardAuction(@RequestParam("ownedId") Integer ownedId,
			@RequestParam("startPrice") Integer startPrice, @RequestParam("directPrice") Integer directPrice,
			@RequestParam("endTime") String endTime) throws ParseException {

		Date date = null;

		date = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);

		Date newDate = cal.getTime();

		cRService.insertCardReleasedAuction(ownedId, startPrice, directPrice, newDate);

		return "redirect:/card/releasedList";
	}

	@GetMapping("/all/{status}")
	@ResponseBody
	public ArrayList<CardReleasedDto> showAllRelease(@PathVariable(name = "status") Integer status) {

		// 取得所有card
		List<CardReleased> allReleaseCard = cRService.showAllReleased();
		// 過濾掉下架的card
		List<CardReleased> listedCards = allReleaseCard.stream().filter(card -> card.getReleasedStatus() == 1)
				.collect(Collectors.toList());

		ArrayList<CardReleasedDto> cDList = new ArrayList<CardReleasedDto>();

		listedCards.stream().forEach(cardReleased -> {

			CardOwned cardOwned = cRService.showOnwedDetail(cardReleased.getFkOwnedId());
			Card card = cRService.findCardById(cardOwned.getFkCardId());

			Member member = mService.findMemberById(cardOwned.getFkMemberId());
			String memberName = member.getMemberName();

			CardReleasedDto cardDto = new CardReleasedDto();

			cardDto.setReleasedId(cardReleased.getReleasedId());
			cardDto.setOwnedId(cardOwned.getOwnedId());
			cardDto.setDirectPrice(cardReleased.getDirectPrice());
			cardDto.setStartPrice(cardReleased.getStartPrice());
			cardDto.setType(cardReleased.getType());
			cardDto.setStartTime(cardReleased.getStartTime());
			cardDto.setEndTime(cardReleased.getEndTime());
			cardDto.setReleasedStatus(cardReleased.getReleasedStatus());
			cardDto.setCardId(card.getCardId());
			cardDto.setMemberId(cardOwned.getFkMemberId());
			cardDto.setCardStatus(cardOwned.getCardStatus());
			cardDto.setCardName(card.getCardName());
			cardDto.setCardStar(card.getCardStar());
			cardDto.setMemberName(memberName);

			cDList.add(cardDto);
		});

		if (status == 2) {
			Collections.sort(cDList, new Comparator<CardReleasedDto>() {
				@Override
				public int compare(CardReleasedDto dto1, CardReleasedDto dto2) {
					System.out.println("dto1: " + dto1.toString());
					System.out.println("dto2: " + dto2.toString());
					return dto1.getEndTime().compareTo(dto2.getEndTime());
				}
			});
			System.out.println(cDList.toString());
		} else if (status == 3) {
			/* 星數大到小 */
			cDList.sort((a, b) -> b.getCardStar() - a.getCardStar());
		} else if (status == 4) {
			/* 星數小到大 */
			cDList.sort((a, b) -> a.getCardStar() - b.getCardStar());
		} else if (status == 5) {
			/* 價格大到小 */
			cDList.sort((a, b) -> b.getDirectPrice() - a.getDirectPrice());
		} else if (status == 6) {
			/* 價格大到小 */
			cDList.sort((a, b) -> a.getDirectPrice() - b.getDirectPrice());
		} else {
			/* 預設為最新上架 */
			cDList.sort((a, b) -> b.getReleasedId() - a.getReleasedId());
		}

		return cDList;
	}

//	@GetMapping("/edit/{releasedId}")
//	@ResponseBody
//	public CardReleased editReleased()

	@GetMapping("/card/{releasedId}")
	@ResponseBody
	public ArrayList<CardReleasedDto> showCardRelease(@PathVariable(name = "releasedId") Integer releasedId) {

		// 取得所有card
		CardReleased cardReleased = cRService.showCardReleased(releasedId);

		ArrayList<CardReleasedDto> cDList = new ArrayList<CardReleasedDto>();

		CardOwned cardOwned = cRService.showOnwedDetail(cardReleased.getFkOwnedId());
		Card card = cRService.findCardById(cardOwned.getFkCardId());

		Member member = mService.findMemberById(cardOwned.getFkMemberId());
		String memberName = member.getMemberName();

		CardReleasedDto cardDto = new CardReleasedDto();

		cardDto.setReleasedId(cardReleased.getReleasedId());
		cardDto.setOwnedId(cardOwned.getOwnedId());
		cardDto.setDirectPrice(cardReleased.getDirectPrice());
		cardDto.setStartPrice(cardReleased.getStartPrice());
		cardDto.setType(cardReleased.getType());
		cardDto.setStartTime(cardReleased.getStartTime());
		cardDto.setEndTime(cardReleased.getEndTime());
		cardDto.setReleasedStatus(cardReleased.getReleasedStatus());
		cardDto.setCardId(card.getCardId());
		cardDto.setMemberId(cardOwned.getFkMemberId());
		cardDto.setCardStatus(cardOwned.getCardStatus());
		cardDto.setCardName(card.getCardName());
		cardDto.setCardStar(card.getCardStar());
		cardDto.setMemberName(memberName);

		cDList.add(cardDto);

		return cDList;

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

	@PostMapping("/buy")
	@ResponseBody
	public String buyCardReleased(/* @RequestBody String body, */ @RequestParam("releasedId") Integer releasedId,
			@RequestParam("ownedId") Integer ownedId, @RequestParam("price") Integer price, HttpSession session) {
//		JSONObject jsonObject = new JSONObject(body);
//		String price = jsonObject.getString("price");
		String buyCard = cRService.buyCard(releasedId, ownedId, price, session);

		if (buyCard != null) {
			Member member = (Member) session.getAttribute("member");
			Integer memberId = member.getMemberId();
			member = cListService.findMember(memberId);
			session.setAttribute("member", member);
			return "購買成功";
		}

		return "購買失敗";
	}

	@PostMapping("/discontinued")
	@ResponseBody
	public String discontinuedReleased(@RequestParam("releasedId") Integer releasedId,
			@RequestParam("ownedId") Integer ownedId) {
		String discontinued = cRService.discontinued(releasedId, ownedId);

		if (discontinued != null) {
			return "下架成功";
		}

		return "下架失敗";
	}

	@PostMapping("/edit")
	public String editMyReleased(@RequestParam("releasedId") Integer releasedId,
			@RequestParam("directPrice") Integer directPrice, @RequestParam("endTime") String endTime)
			throws ParseException {

		Date date = null;

		date = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);

		Date newDate = cal.getTime();

		String editMyReleased = cRService.editMyReleased(releasedId, directPrice, newDate);

		if (editMyReleased != null) {
			return "redirect:/card/releasedList";
		}

		return null;

	}

}
