package tw.com.eeit162.meepleMasters.lyh.model.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import tw.com.eeit162.meepleMasters.lyh.model.bean.CardReleased;

public interface CardReleasedDao extends JpaRepository<CardReleased, Integer> {

	@Query("from CardReleased join CardOwned on ownedId = fk_ownedId where fk_memberId = :memberId")
	List<CardReleased> myReleased(@Param("memberId") Integer memberId);

	@Modifying(clearAutomatically = true)
	@Query("update CardReleased set releasedStatus = 2 where releasedId = :releasedId")
	Integer updateReleased(@Param("releasedId") Integer releasedId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update CardReleased set directPrice = :directPrice, endTime = :endTime where releasedId = :releasedId")
	Integer editMyReleased(@Param("releasedId") Integer releasedId, @Param("directPrice") Integer directPrice, @Param("endTime") Date endTime);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update CardReleased set startPrice = :startPrice, directPrice = :directPrice, endTime = :endTime where releasedId = :releasedId")
	Integer editMyAuction(@Param("releasedId") Integer releasedId, @Param("startPrice") Integer startPrice, @Param("directPrice") Integer directPrice, @Param("endTime") Date endTime);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update CardReleased set endTime = :endTime where releasedId = :releasedId")
	Integer addFiveMinutes(@Param("releasedId") Integer releasedId, @Param("endTime") Date endTime);
	
}
