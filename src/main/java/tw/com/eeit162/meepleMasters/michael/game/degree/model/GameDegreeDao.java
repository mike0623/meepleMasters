package tw.com.eeit162.meepleMasters.michael.game.degree.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameDegreeDao extends JpaRepository<GameDegree, Integer> {
	
	@Query("from GameDegree where fkMemberId = :fkMemberId and fkProductId = :fkProductId")
	GameDegree findGameDegreeByBoth(@Param("fkMemberId") Integer fkMemberId,@Param("fkProductId") Integer fkProductId);
	
	@Modifying
	@Query("update GameDegree set score = :score where fkMemberId = :fkMemberId and fkProductId = :fkProductId")
	void updateGameDegreeByBoth(@Param("score") Integer score,@Param("fkMemberId") Integer fkMemberId,@Param("fkProductId") Integer fkProductId);

}
