package tw.com.eeit162.meepleMasters.michael.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.michael.message.model.FriendMessage;
import tw.com.eeit162.meepleMasters.michael.message.model.FriendMessageDao;

@Service
public class FriendMessageService {
	@Autowired
	private FriendMessageDao messageDao;
	
	public void insertFriendMessage() {
		FriendMessage friendMessage = new FriendMessage();
		friendMessage.setFkSenderId(1);
		friendMessage.setFkReceiverId(2);
		friendMessage.setMessageContext("肚子餓了");
		
		messageDao.save(friendMessage);
		
	
	}
}
