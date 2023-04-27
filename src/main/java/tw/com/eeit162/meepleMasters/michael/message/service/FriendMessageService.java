package tw.com.eeit162.meepleMasters.michael.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.eeit162.meepleMasters.michael.message.model.FriendMessage;
import tw.com.eeit162.meepleMasters.michael.message.model.FriendMessageDao;

@Service
public class FriendMessageService {
	@Autowired
	private FriendMessageDao messageDao;
	
	public void insertFriendMessage(FriendMessage friendMessage) {
		messageDao.save(friendMessage);
	}
	
	@Transactional
	public void updateHaveRead(Integer fkSenderId,Integer fkReceiverId) {
		messageDao.updateHaveRead(fkSenderId, fkReceiverId);
	}
	
	public List<FriendMessage> findAllBySenderAndReceiver(Integer fkSenderId,Integer fkReceiverId){
		return messageDao.findAllBySenderAndReceiver(fkSenderId, fkReceiverId);
	}
	
	public Integer findNotRead(Integer fkSenderId,Integer fkReceiverId) {
		return messageDao.findNotRead(fkSenderId, fkReceiverId);
		
	}
}
