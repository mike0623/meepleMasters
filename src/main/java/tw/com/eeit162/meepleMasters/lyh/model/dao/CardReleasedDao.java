package tw.com.eeit162.meepleMasters.lyh.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tw.com.eeit162.meepleMasters.lyh.model.bean.CardReleased;

public interface CardReleasedDao extends JpaRepository<CardReleased, Integer> {

	@Query("from CardReleased join CardOwned on ownedId = fk_ownedId where fk_memberId = :memberId")
	List<CardReleased> myReleased(@Param("memberId") Integer memberId);

	@Modifying(clearAutomatically = true)
	@Query("update CardReleased set releasedStatus = 2 where releasedId = :releasedId")
	Integer updateReleased(@Param("releasedId") Integer releasedId);
	
	
	
}
