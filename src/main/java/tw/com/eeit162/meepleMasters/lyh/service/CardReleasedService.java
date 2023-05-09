package tw.com.eeit162.meepleMasters.lyh.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.eeit162.meepleMasters.lyh.model.bean.Card;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardOwned;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardReleased;
import tw.com.eeit162.meepleMasters.lyh.model.dao.CardDao;
import tw.com.eeit162.meepleMasters.lyh.model.dao.CardOwnedDao;
import tw.com.eeit162.meepleMasters.lyh.model.dao.CardReleasedDao;

@Service
public class CardReleasedService {
	
	@Autowired
	private CardDao cDao;
	
	@Autowired
	private CardOwnedDao cODao;
	
	@Autowired
	private CardReleasedDao cRDao;
	
	public List<CardOwned> showOwnedCard(Integer memberId) {
		
		List<CardOwned> ownedCard = cODao.ownedCardStarOrderASC(memberId);
//		System.out.println(ownedCard.toString());
		
		if (ownedCard.isEmpty()) {
			return null;
		}
		
		return ownedCard;
	}
	
	public CardOwned showOnwedDetail(Integer ownedId) {
		Optional<CardOwned> owned = cODao.findById(ownedId);
		
		if (owned.isEmpty()) {
			return null;
		}
		
		return owned.get();
	}
	
	public Card findCardById(Integer cardId) {
		Optional<Card> card = cDao.findById(cardId);
		
		if (card.isEmpty()) {
			return null;
		}
		
		return card.get();
	}
	
	
	@Transactional
	public CardReleased insertCardReleasedDirect(Integer ownedId, Integer price, Date endTime) {
		
		CardReleased cr = new CardReleased();
		
		cODao.updateCardStatusToSell(ownedId);
		
		cr.setFkOwnedId(ownedId);
		cr.setDirectPrice(price);
		cr.setType(1);
		cr.setStartTime(new Date());
		cr.setEndTime(endTime);
		cr.setReleasedStatus(1);
		
		CardReleased newRelease = cRDao.save(cr);
		
		return newRelease;
	}
	
	public List<CardReleased> showMyReleased(Integer memberId) {
		List<CardReleased> releaseList = cRDao.myReleased(memberId);
		
		if (releaseList.isEmpty()) {
			return null;
		}
		
		return releaseList;
	}
	
	public List<CardReleased> showAllReleased() {
		
		List<CardReleased> releaseList = cRDao.findAll();
		
		if (releaseList.isEmpty()) {
			return null;
		}
		
		return releaseList;
	}
	


}
