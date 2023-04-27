package tw.com.eeit162.meepleMasters.michael.message.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.michael.message.model.MessageText;
import tw.com.eeit162.meepleMasters.michael.message.model.MessageTextDao;

@Service
public class MessageTextService {
	
	@Autowired
	private MessageTextDao textDao;
	
	public String findById(Integer textId) {
		Optional<MessageText> op = textDao.findById(textId);
		if(op != null) {
			return op.get().getMsgText();
		}
		return null;
	}
	
	public Integer insertMessageText(String text) {
		MessageText messageText = new MessageText();
		messageText.setMsgText(text);
		
		MessageText newMessageText = textDao.save(messageText);
		return newMessageText.getMessageTextId();
	}
	
}
