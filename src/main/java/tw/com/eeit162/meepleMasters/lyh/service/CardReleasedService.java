package tw.com.eeit162.meepleMasters.lyh.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		
		List<CardOwned> ownedCard = cODao.findByFkMemberId(memberId);
		
		if (ownedCard.isEmpty()) {
			return null;
		}
		
		return ownedCard;
	}
	
	
	public CardReleased insertNewRelease(Integer ownedId, Integer type, Date endTime) {
		
		CardReleased cr = new CardReleased();
		
		cr.setFkOwnedId(ownedId);
		cr.setType(type);
		cr.setStartTime(new Date());
		cr.setEndTime(endTime);
		
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
