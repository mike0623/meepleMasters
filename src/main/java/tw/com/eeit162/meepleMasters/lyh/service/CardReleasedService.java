package tw.com.eeit162.meepleMasters.lyh.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jack.model.dao.MemberDao;
import tw.com.eeit162.meepleMasters.lyh.model.bean.Card;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardAuction;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardDirect;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardOwned;
import tw.com.eeit162.meepleMasters.lyh.model.bean.CardReleased;
import tw.com.eeit162.meepleMasters.lyh.model.dao.CardAuctionDao;
import tw.com.eeit162.meepleMasters.lyh.model.dao.CardDao;
import tw.com.eeit162.meepleMasters.lyh.model.dao.CardDirectDao;
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
	
	@Autowired
	private MemberDao mDao;
	
	@Autowired
	private CardDirectDao cDDao;
	
	@Autowired
	private CardAuctionDao cADao;
	
	
	public List<CardOwned> showMyCard(Integer memberId) {
		
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
	
	public CardAuction findAuctionById(Integer releasedId) {
		List<CardAuction> cardAuction = cADao.findByFkReleasedId(releasedId);
		
		if (cardAuction == null) {
			return null;
		}
		
		return cardAuction.get(0);
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
	
	@Transactional
	public String insertCardReleasedAuction(Integer ownedId, Integer startPrice, Integer directPrice, Date endTime) {
		
		CardReleased cr = new CardReleased();
		
		cODao.updateCardStatusToSell(ownedId);
		System.out.println("service");
		cr.setFkOwnedId(ownedId);
		cr.setStartPrice(startPrice);
		if (directPrice != null) {
			cr.setDirectPrice(directPrice);
		} 
		cr.setType(2);
		cr.setStartTime(new Date());
		cr.setEndTime(endTime);
		cr.setReleasedStatus(1);
		
		CardReleased newRelease = cRDao.save(cr);
		
		Integer releasedId = newRelease.getReleasedId();
		
		CardAuction cA = new CardAuction();
		cA.setFkReleasedId(releasedId);
		CardAuction newAuction = cADao.save(cA);
		
		if (newRelease != null && newAuction != null) {
			return "success";
		}
		
		return null;
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
	
	@Transactional
	public CardReleased showCardReleased(Integer releasedId) {
		
		Optional<CardReleased> card = cRDao.findById(releasedId);
		
		if (card.isEmpty()) {
			return null;
		}
		
		return card.get();
	}
	
	@Transactional
	public String buyCard(Integer releasedId, Integer ownedId, Integer price, HttpSession session) {
		
//		找到登入的會員
		Member member = (Member)session.getAttribute("member");
		Integer memberId = member.getMemberId();
		Integer memberCoin = member.getMemberCoin();
		
//		確認會員的錢是否足夠
		if (memberCoin < price) {
			return null;
		}
		
//		更新卡片狀態、扣購買會員的錢
		Integer updateReleased = cRDao.updateReleased(releasedId);
		Integer updateCardOwned = cODao.updateCardOwned(ownedId);
		Integer buyerUpdateCoin = mDao.updateMemberCoin(memberId, -price);
		
//		尋找原本CardOwned的資訊
		Optional<CardOwned> cardOwned = cODao.findById(ownedId);
		
		if (cardOwned.isEmpty()) {
			return null;
		}
		
//		建立新的CardOwned
		CardOwned cO = new CardOwned();

		cO.setFkCardId(cardOwned.get().getFkCardId());
		cO.setFkMemberId(memberId);
		cO.setOwnedTime(new Date());
		cO.setCardStatus(1);

		CardOwned newCard = cODao.save(cO);
		
//		尋找販賣卡片的會員後加錢
		Integer seller = cardOwned.get().getFkMemberId();
		Integer sellerUpdateCoin = mDao.updateMemberCoin(seller, price);
		
//		建立CardDirect
		CardDirect cD = new CardDirect();
		
		cD.setFkReleasedId(releasedId);
		cD.setFkPurchaserId(memberId);
		cD.setPurchaseTime(new Date());
		
		CardDirect cardDirect = cDDao.save(cD);
		
		if (updateReleased != 0 && updateCardOwned != 0 && buyerUpdateCoin != 0 && sellerUpdateCoin != 0 && newCard != null && cardDirect != null) {
			return "success";
		}
		
		return null;
		
	}
	
	@Transactional
	public String discontinued(Integer releasedId, Integer ownedId) {
		
		Integer updateReleased = cRDao.updateReleased(releasedId);
		Integer continuedCardOwned = cODao.continuedCardOwned(ownedId);
		
		if (updateReleased != 0 && continuedCardOwned != 0) {
			return "success";
		}
		
		return null;
	}
	
	public String editMyReleased(Integer releasedId, Integer directPrice, Date endTime) {
		Integer editMyReleased = cRDao.editMyReleased(releasedId, directPrice, endTime);
		
		if (editMyReleased != 0) {
			return "success";
		}
		
		return null;
	}
	


}
