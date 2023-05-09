package tw.com.eeit162.meepleMasters.michael.message.model;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendMessageDao extends JpaRepository<FriendMessage, Integer> {
	
	@Modifying
	@Query("update FriendMessage set haveRead = 1 where fkSenderId = :fkSenderId and fkReceiverId = :fkReceiverId")
	void updateHaveRead(@Param("fkSenderId") Integer fkSenderId, @Param("fkReceiverId") Integer fkReceiverId);
	
	
	@Query("from FriendMessage where (fkSenderId = :fkSenderId and fkReceiverId = :fkReceiverId) or (fkSenderId = :fkReceiverId and fkReceiverId = :fkSenderId) order by friendMessageCreatedDate")
	List<FriendMessage> findAllBySenderAndReceiver(@Param("fkSenderId") Integer fkSenderId, @Param("fkReceiverId") Integer fkReceiverId);
	
	
	@Query("select count(*) from FriendMessage where fkSenderId = :fkSenderId and fkReceiverId = :fkReceiverId and haveRead = 0")
	Integer findNotRead(@Param("fkSenderId") Integer fkSenderId, @Param("fkReceiverId") Integer fkReceiverId);
	
	

	
}
