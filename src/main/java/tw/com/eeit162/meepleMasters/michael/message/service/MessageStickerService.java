package tw.com.eeit162.meepleMasters.michael.message.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.michael.message.model.MessageSticker;
import tw.com.eeit162.meepleMasters.michael.message.model.MessageStickerDao;
import tw.com.eeit162.meepleMasters.michael.message.model.MessageText;

@Service
public class MessageStickerService {
	
	@Autowired
	private MessageStickerDao stickerDao;
	
	
	public Integer insertMessageSticker(Integer fkStickerId) {
		MessageSticker messageSticker = new MessageSticker();
		messageSticker.setFkStickerId(fkStickerId);
		
		MessageSticker newMessageSticker = stickerDao.save(messageSticker);
		return newMessageSticker.getMessageStickerId();
	}
	
	public Integer findById(Integer stickerId) {
		Optional<MessageSticker> op = stickerDao.findById(stickerId);
		if(op != null) {
			return op.get().getFkStickerId();
		}
		return null;
	}
	
}
