package tw.com.eeit162.meepleMasters.lyh.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.lyh.controller.RandomCard;
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
	
	RandomCard randomCard = new RandomCard();
	
	public CardOwned insertCardToList(Integer memberId) {
		
		int cardId = randomCard.cardId();
//		System.out.println(cardId);
		
		Optional<Card> option = cDao.findById(cardId);
		
		if (option.isEmpty()) {
			return null;
		}
		
//		Card card = option.get();
		
		CardOwned cO = new CardOwned();
		
		cO.setFkCardId(cardId);
		cO.setFkMemberId(memberId);
		cO.setOwnedTime(new Date());
		cO.setCardStatus(1);
		
		CardOwned newCard = cODao.save(cO);
		
		return newCard;
	}
	
	public List<CardOwned> listOwnedCard(Integer memberId) {
		
		List<CardOwned> option = cODao.findByFkMemberId(memberId);
		
		if (option.isEmpty()) {
			return null;
		}
		
		return option;
		
	}
	
	public Card findById(Integer cardId) {
		
		Optional<Card> option = cDao.findById(cardId);
		
		if (option.isEmpty()) {
			return null;
		}
		
		return option.get();
	}
	
}
