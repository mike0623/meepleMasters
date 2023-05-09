package tw.com.eeit162.meepleMasters.michael.message.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageStickerDao extends JpaRepository<MessageSticker, Integer> {

	
	//測試join
//	@Query("from FriendMessage"
//			+ " join MessageText on fk_messageContent = messageTextId"
//			+ " where friendMessageId = :x")
//	List<Object[]> findStickerByxxx(@Param("x") Integer x); 
}
