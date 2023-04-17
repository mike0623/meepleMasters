package tw.com.eeit162.meepleMasters.michael.friend.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendDao extends JpaRepository<Friend, Integer> {
	
	@Query("from Friend where fkMemberAId = :id or fkMemberBId = :id" )
	List<Friend> findFriendByMemberId(@Param("id") Integer id);
	
	@Query("from Friend where fkMemberAId = :memberAId and fkMemberBId = :memberBId")
	Friend findFriendByBoth(@Param("memberAId") Integer memberAId,@Param("memberBId") Integer memberBId);
	

	
	
	
}
