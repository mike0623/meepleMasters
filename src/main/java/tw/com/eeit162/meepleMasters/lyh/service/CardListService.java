package tw.com.eeit162.meepleMasters.lyh.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jack.model.dao.MemberDao;
import tw.com.eeit162.meepleMasters.lyh.model.bean.Card;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardOwned;
import tw.com.eeit162.meepleMasters.lyh.model.dao.CardDao;
import tw.com.eeit162.meepleMasters.lyh.model.dao.CardOwnedDao;

@Service
public class CardListService {

	@Autowired
	private CardDao cDao;

	@Autowired
	private CardOwnedDao cODao;
	
	@Autowired
	private MemberDao mDao;
	
	public Member findMember(Integer memberId) {
		Optional<Member> member = mDao.findById(memberId);
		
		if (!member.isEmpty()) {
			return member.get();
		} 
		
		return null;
		
	}

	@Transactional
	public CardOwned insertCardToList(Integer memberId) {
		
		Integer memberCoin = mDao.findById(memberId).get().getMemberCoin();
		
		if (memberCoin < 100) {
			return null;
		}
		
		mDao.updateMemberCoin(memberId, -100);

		int cardId = randomCardId();

		CardOwned cO = new CardOwned();

		cO.setFkCardId(cardId);
		cO.setFkMemberId(memberId);
		cO.setOwnedTime(new Date());
		cO.setCardStatus(1);

		CardOwned newCard = cODao.save(cO);

		return newCard;
	}

	public List<CardOwned> listOwnedCard(Integer memberId) {

		List<CardOwned> ownedCard = cODao.findByMemberId(memberId);

		if (ownedCard.isEmpty()) {
			return null;
		}
		return ownedCard;
	}
	
	public List<CardOwned> listCardOrderByStarDESC(Integer memberId) {
		List<CardOwned> ownedCard = cODao.ownedCardStarOrderDESC(memberId);
		
		if (ownedCard.isEmpty()) {
			return null;
		}
		return ownedCard;
	}
	
	public List<CardOwned> listCardOrderByStarASC(Integer memberId) {
		List<CardOwned> ownedCard = cODao.ownedCardStarOrderASC(memberId);
		
		if (ownedCard.isEmpty()) {
			return null;
		}
		return ownedCard;
	}

	public Card findById(Integer cardId) {

		Optional<Card> option = cDao.findById(cardId);

		if (option.isEmpty()) {
			return null;
		}

		return option.get();
	}

	public List<Card> findAllCard() {

		List<Card> allCard = cDao.findAll();

		if (allCard.isEmpty()) {
			return null;
		}
		return allCard;
	}
	
	

	
	
	List<Card> allCard = new ArrayList<>();
	List<Integer> card1 = new ArrayList<>();
	List<Integer> card2 = new ArrayList<>();
	List<Integer> card3 = new ArrayList<>();
	List<Integer> card4 = new ArrayList<>();
	List<Integer> card5 = new ArrayList<>();
	

	public int randomCardId() {

		// 設定每種卡片的機率（以百分比表示）
		int[] cardProbabilities = {2, 6, 10, 22, 60};

		// 計算所有卡片的總機率
		int totalProbability = 0;
		for (int i = 0; i < cardProbabilities.length; i++) {
			totalProbability += cardProbabilities[i];
		}

		// System.out.println(totalProbability);

		// 生成一個隨機數，表示抽到的卡片星數
		Random random = new Random();
		int randomNumber = (random.nextInt(Math.abs(totalProbability)+1))-1;
		System.out.println("randomNumber: " + randomNumber);

		int cumulativeProbability = 0;
		int[] star = new int[5];

		for (int i = 0; i < cardProbabilities.length; i++) {
			cumulativeProbability += cardProbabilities[i];
			star[i] = cumulativeProbability;
//	            System.out.println(cumulativeProbability);
		}

		int card = 0;

		// 輸出星數
		if (randomNumber < (star[0])) {
			System.out.println("五星");
			card = star5();
		} else if (randomNumber < (star[1])) {
			System.out.println("四星");
			card = star4();
		} else if (randomNumber < (star[2])) {
			System.out.println("三星");
			card = star3();
		} else if (randomNumber < (star[3])) {
			System.out.println("二星");
			card = star2();
		} else {
			System.out.println("一星");
			card = star1();
		}

		return card;
	}

	public void cardStar() {
		
		allCard = findAllCard();

		for (int i = 0; i < allCard.size(); i++) {
			Integer star = allCard.get(i).getCardStar();

			switch (star) {
			case 1:
				card1.add(allCard.get(i).getCardId());
				break;
			case 2:
				card2.add(allCard.get(i).getCardId());
				break;
			case 3:
				card3.add(allCard.get(i).getCardId());
				break;
			case 4:
				card4.add(allCard.get(i).getCardId());
				break;
			case 5:
				card5.add(allCard.get(i).getCardId());
				break;
			default:
				break;
			}
		}
	}

	public int star5() {
		cardStar();
		Random random = new Random();
		int randomNumber = (random.nextInt(Math.abs(card5.size())+1))-1;

		System.out.println(card5.get(randomNumber));
		return card5.get(randomNumber);
	}

	public int star4() {
		cardStar();
		Random random = new Random();
		int randomNumber = (random.nextInt(Math.abs(card4.size())+1))-1;

		System.out.println(card4.get(randomNumber));
		return card4.get(randomNumber);
	}

	public int star3() {
		cardStar();
		Random random = new Random();
		int randomNumber = (random.nextInt(Math.abs(card3.size())+1))-1;

		System.out.println(card3.get(randomNumber));
		return card3.get(randomNumber);
	}

	public int star2() {
		cardStar();
		Random random = new Random();
		int randomNumber = (random.nextInt(Math.abs(card2.size())+1))-1;

		System.out.println(card2.get(randomNumber));
		return card2.get(randomNumber);
	}

	public int star1() {
		cardStar();
		Random random = new Random();
		int randomNumber = (random.nextInt(Math.abs(card1.size())+1))-1;

		System.out.println(card1.get(randomNumber));
		return card1.get(randomNumber);
	}
	
	public List<Integer> ownedCardStar(String json) {
		JSONObject jsonObject = new JSONObject(json);
		int int1 = jsonObject.getInt("memberId");
		
		List<Object[]> ownedCard = cODao.ownedCardStar(int1);
		List<Integer> list = new ArrayList<>();
		for(Object[] o :ownedCard) {
			System.out.println(o.length);
			JSONObject jsonObject2 = new JSONObject(o[0]);
			System.out.println("ownedId: "+jsonObject2.getInt("ownedId"));
			System.out.println("fkCardId: "+jsonObject2.has("fkCardId"));
			
			
			JSONObject jsonObject3 = new JSONObject(o[1]);
			System.out.println("cardStar: "+jsonObject3.getInt("cardStar"));
			System.out.println("--------------------------------------------");
		}
		return list;
	}

}
