package tw.com.eeit162.meepleMasters.michael.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.eeit162.meepleMasters.michael.message.model.ChatRoomOrder;
import tw.com.eeit162.meepleMasters.michael.message.model.ChatRoomOrderDao;

@Service
public class ChatRoomOrderService {
	
	@Autowired
	private ChatRoomOrderDao orderDao;
	
	public void insertChatRoomOrder(ChatRoomOrder chatRoomOrder) {
		orderDao.save(chatRoomOrder);
	}
	
	@Transactional
	public void deleteChatRoomOrderByOwnerId(Integer fkOwner) {
		orderDao.deleteByFkOwner(fkOwner);
	}
	
	
	public List<ChatRoomOrder> findByFkOwner(Integer fkOwner){
		return orderDao.findByFkOwner(fkOwner);
	}
	
	public boolean isExist(Integer fkOwner,Integer fkChatToWhom) {
		ChatRoomOrder chatRoomOrder = orderDao.findByBoth(fkOwner, fkChatToWhom);
		if(chatRoomOrder != null) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public void deleteByBoth(Integer fkOwner,Integer fkChatToWhom) {
		orderDao.deleteByBoth(fkOwner, fkChatToWhom);
	}
	
}
