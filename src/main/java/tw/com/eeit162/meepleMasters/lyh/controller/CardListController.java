package tw.com.eeit162.meepleMasters.lyh.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.lyh.model.bean.Card;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardOwned;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardReleased;
import tw.com.eeit162.meepleMasters.lyh.model.dto.CardOwnedDto;
import tw.com.eeit162.meepleMasters.lyh.model.dto.CardReleasedDto;
import tw.com.eeit162.meepleMasters.lyh.service.CardListService;

@Controller
@RequestMapping(path = {"/card"})
public class CardListController {

	@Autowired
	private CardListService cListService;

	@PostMapping("/getNewCard")
	@ResponseBody
	public String getNewCard(HttpSession session) {

		Card card = new Card();
		
		Member member = (Member)session.getAttribute("member");
		Integer memberId = member.getMemberId();
		
		List<CardOwned> oldCardList = cListService.listOwnedCard(memberId);
		CardOwned newCard = cListService.insertCardToList(memberId);
		
//		System.out.println("newCard: "+newCard.toString());
//		System.out.println("oldCardList: "+oldCardList.toString());

		card = cListService.findById(newCard.getFkCardId());
		
		JSONObject jsonObject = new JSONObject();
		if (oldCardList != null) {
			for (int i = 0; i < oldCardList.size(); i++) {
				if (newCard.getFkCardId().equals(oldCardList.get(i).getFkCardId())) {
					jsonObject.put("isNew", false);
					System.out.println(newCard.getFkCardId());
					System.out.println(oldCardList.get(i).getFkCardId());
					break;
				} else {
					jsonObject.put("isNew", true);
				}
			}
		} else {
			jsonObject.put("isNew", true);
		}
		
		member = cListService.findMember(memberId);
		session.setAttribute("member", member);
		Integer memberCoin = member.getMemberCoin();
		System.out.println(member.getMemberCoin());
		
		jsonObject.put("newCard", card);
		jsonObject.put("memberCoin", memberCoin);
		return jsonObject.toString();
	}

	@GetMapping("/mycard/{memberId}")
	public String listOwnedCard(@PathVariable(name = "memberId") Integer memberId, Model model) {

		List<CardOwned> list = cListService.listOwnedCard(memberId);

		ArrayList<Card> cardList = new ArrayList<>();
		ArrayList<Card> sellList = new ArrayList<>();

		if (list != null && !list.isEmpty()) {

			for (int i = 0; i < list.size(); i++) {
				if ((list.get(i).getCardStatus()) == 1) {
					cardList.add(cListService.findById(list.get(i).getFkCardId()));
				}
				if ((list.get(i).getCardStatus()) == 3) {
					sellList.add(cListService.findById(list.get(i).getFkCardId()));
				}
			}
			model.addAttribute("sellList", sellList);
			model.addAttribute("cardList", cardList);

			return "lyh/mycard";
		}

		return "lyh/mycard";
	}
	
	@GetMapping("/mycard/{memberId}/getOrder")
	@ResponseBody
	public ArrayList<CardOwnedDto> listOwnedCardAjax(@PathVariable(name = "memberId") Integer memberId) {

		List<CardOwned> list = cListService.listOwnedCard(memberId);
		
		List<CardOwned> listedCards = list.stream().filter(card -> card.getCardStatus() != 2)
				.collect(Collectors.toList());

		ArrayList<CardOwnedDto> cardList = new ArrayList<CardOwnedDto>();
		
		listedCards.stream().forEach(cardOwned -> {

			Card card = cListService.findById(cardOwned.getFkCardId());
			CardOwnedDto cardDto = new CardOwnedDto();
			
			cardDto.setOwnedId(cardOwned.getOwnedId());
			cardDto.setCardId(cardOwned.getFkCardId());
			cardDto.setCardStatus(cardOwned.getCardStatus());
			cardDto.setCardName(card.getCardName());
			cardDto.setCardStar(card.getCardStar());
			
			cardList.add(cardDto);
		});

		return cardList;
	}

//	沒用到了
	@GetMapping("/mycard/{memberId}/starOrderDESC")
	@ResponseBody
	public String listOwnedCardOrderByStarDESC(@PathVariable(name = "memberId") Integer memberId) {
		List<CardOwned> list = cListService.listCardOrderByStarDESC(memberId);

		ArrayList<Card> cardList = new ArrayList<>();
		ArrayList<Card> sellList = new ArrayList<>();

		if (list != null && !list.isEmpty()) {

			for (int i = 0; i < list.size(); i++) {
				if ((list.get(i).getCardStatus()) == 1) {
					cardList.add(cListService.findById(list.get(i).getFkCardId()));
				}
				if ((list.get(i).getCardStatus()) == 3) {
					sellList.add(cListService.findById(list.get(i).getFkCardId()));
				}
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sellList", sellList);
			jsonObject.put("cardList", cardList);
			return jsonObject.toString();
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
	

	@PostMapping("/test")
	@ResponseBody
	public List<Integer> test(@RequestBody String json) {
		return cListService.ownedCardStar(json);
	}

}
