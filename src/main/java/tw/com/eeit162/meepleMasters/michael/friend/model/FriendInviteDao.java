package tw.com.eeit162.meepleMasters.michael.friend.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendInviteDao extends JpaRepository<FriendInvite, Integer> {
	
	@Query("from FriendInvite where fkInviterId = :id")
	List<FriendInvite> findFriendInviteByInviterId(@Param("id") Integer memberId);
	
	@Query("from FriendInvite where fkAccepterId = :id")
	List<FriendInvite> findFriendInviteByAccepterId(@Param("id") Integer memberId);
	
	@Query("from FriendInvite where fkInviterId = :fkInviterId and fkAccepterId = :fkAccepterId")
	FriendInvite findFriendInviteByBoth(@Param("fkInviterId") Integer fkInviterId,@Param("fkAccepterId") Integer fkAccepterId);
	
}
