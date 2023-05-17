package tw.com.eeit162.meepleMasters.lyh.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import tw.com.eeit162.meepleMasters.lyh.model.bean.CardOwned;

public interface CardOwnedDao extends JpaRepository<CardOwned, Integer> {
	
	@Query("from CardOwned where fk_memberId = :memberId order by ownedId DESC")
	List<CardOwned> findByMemberId(Integer memberId);
	
	@Query("from CardOwned join Card on cardId = fk_cardId where fk_memberId = :memberId order by cardStar DESC")
	List<Object[]> ownedCardStar(@Param("memberId") Integer memberId);
	
	@Query("from CardOwned join Card on cardId = fk_cardId where fk_memberId = :memberId order by cardStar DESC, cardId ASC")
	List<CardOwned> ownedCardStarOrderDESC(@Param("memberId") Integer memberId);
	
	@Query("from CardOwned join Card on cardId = fk_cardId where fk_memberId = :memberId order by cardStar ASC, cardId ASC")
	List<CardOwned> ownedCardStarOrderASC(@Param("memberId") Integer memberId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update CardOwned set cardStatus = 3 where ownedId = :ownedId")
	Integer updateCardStatusToSell(@Param("ownedId") Integer ownedId);
	
	@Modifying(clearAutomatically = true)
	@Query("update CardOwned set cardStatus = 2 where ownedId = :ownedId")
	Integer updateCardOwned(@Param("ownedId") Integer ownedId);
	
	@Modifying(clearAutomatically = true)
	@Query("update CardOwned set cardStatus = 1 where ownedId = :ownedId")
	Integer continuedCardOwned(@Param("ownedId") Integer ownedId);
	
}
