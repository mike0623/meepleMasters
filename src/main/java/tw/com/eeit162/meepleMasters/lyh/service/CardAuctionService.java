package tw.com.eeit162.meepleMasters.lyh.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.lyh.model.bean.CardAuction;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardOwned;
import tw.com.eeit162.meepleMasters.lyh.model.dao.CardAuctionDao;
import tw.com.eeit162.meepleMasters.lyh.model.dao.CardDao;
import tw.com.eeit162.meepleMasters.lyh.model.dao.CardOwnedDao;

@Service
public class CardAuctionService {
	
	private CardDao cDao;
	private CardOwnedDao cODao;
	private CardAuctionDao cADao;
	
	public CardAuction insertNewAuction(Integer memberId, Integer ownedId) {
		
		Optional<CardOwned> ownedCard = cODao.findById(memberId);
		
		if (ownedCard.isEmpty()) {
			return null;
		}
		
		CardAuction ca = new CardAuction();
		
		
		
		
		return null;
	}
	
}
