package tw.com.eeit162.meepleMasters.michael.message.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomOrderDao extends JpaRepository<ChatRoomOrder, Integer> {
	
	@Modifying
	@Query("delete ChatRoomOrder where fkOwner = :fkOwner")
	void deleteByFkOwner(@Param("fkOwner") Integer fkOwner);
	
	@Query("from ChatRoomOrder where fkOwner = :fkOwner order by chatOrderWhenLeave,createdDate desc")
	List<ChatRoomOrder> findByFkOwner(@Param("fkOwner") Integer fkOwner);
	
	@Query("from ChatRoomOrder where fkOwner = :fkOwner and fkChatToWhom = :fkChatToWhom")
	ChatRoomOrder findByBoth(@Param("fkOwner") Integer fkOwner,@Param("fkChatToWhom") Integer fkChatToWhom);
	
	@Modifying
	@Query("delete ChatRoomOrder where fkOwner = :fkOwner and fkChatToWhom = :fkChatToWhom")
	void deleteByBoth(@Param("fkOwner") Integer fkOwner,@Param("fkChatToWhom") Integer fkChatToWhom);
	
}
