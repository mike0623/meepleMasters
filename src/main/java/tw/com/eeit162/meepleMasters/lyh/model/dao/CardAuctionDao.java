package tw.com.eeit162.meepleMasters.lyh.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import tw.com.eeit162.meepleMasters.lyh.model.bean.CardAuction;

public interface CardAuctionDao extends JpaRepository<CardAuction, Integer> {

	@Query("from CardReleased join CardAuction on releasedId = fk_releasedId where releasedId = :releasedId")
	CardAuction findByReleasedId(Integer releasedId);
	
}
