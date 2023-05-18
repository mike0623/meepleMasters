package tw.com.eeit162.meepleMasters.lyh.model.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tw.com.eeit162.meepleMasters.lyh.model.bean.CardAuction;

public interface CardAuctionDao extends JpaRepository<CardAuction, Integer> {

	@Query("from CardAuction where fk_releasedId = :releasedId")
	CardAuction findByReleasedId(Integer releasedId);
	
	@Modifying(clearAutomatically = true)
	@Query("update CardAuction set fk_purchaserId = :memberId, purchasePrice = :purchasePrice, purchaseTime = :purchaseTime where fk_releasedId = :releasedId")
	Integer updateCardAuction(@Param("releasedId") Integer releasedId, @Param("memberId") Integer memberId, @Param("purchasePrice") Integer purchasePrice, @Param("purchaseTime") Date purchaseTime);
	
}
