package tw.com.eeit162.meepleMasters.lyh.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tw.com.eeit162.meepleMasters.lyh.model.bean.CardOwned;

public interface CardOwnedDao extends JpaRepository<CardOwned, Integer> {
	
	List<CardOwned> findByFkMemberId(Integer fkMemberId);
	
	@Query("from CardOwned join Card on cardId = fk_cardId where fk_memberId = :memberId order by cardStar DESC")
	List<Object[]> ownedCardStar(@Param("memberId") Integer memberId);
	
	@Query("from CardOwned join Card on cardId = fk_cardId where fk_memberId = :memberId order by cardStar DESC, cardId ASC")
	List<CardOwned> ownedCardStarOrderDESC(@Param("memberId") Integer memberId);
	
	@Query("from CardOwned join Card on cardId = fk_cardId where fk_memberId = :memberId order by cardStar ASC, cardId ASC")
	List<CardOwned> ownedCardStarOrderASC(@Param("memberId") Integer memberId);
	
}
