package tw.com.eeit162.meepleMasters.michael.message.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.michael.message.model.MessageSticker;
import tw.com.eeit162.meepleMasters.michael.message.model.MessageStickerDao;

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

	
	//測試join
//	public List<Integer> findStickerByxxx(String json) {
//		JSONObject jsonObject = new JSONObject(json);
//		int x = jsonObject.getInt("x");
//		List<Object[]> findStickerByxxx = stickerDao.findStickerByxxx(x);
//		List<Integer> list = new ArrayList<>();
//		for(int i = 0;i<findStickerByxxx.size();i++) {
//			System.out.println(findStickerByxxx.get(i));
//			System.out.println(findStickerByxxx.get(i)[0]);
//			System.out.println(findStickerByxxx.get(i)[1]);
//			JSONObject jsonObject2 = new JSONObject(findStickerByxxx.get(i)[0]);
//			System.out.println(jsonObject2.getInt("friendMessageId"));
//		}
//		
//		
//		return list;
//	}
	
}
