package tw.com.eeit162.meepleMasters.michael.message.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.michael.message.model.Sticker;
import tw.com.eeit162.meepleMasters.michael.message.model.StickerDao;

@Service
public class StickerService {
	
	@Autowired
	private StickerDao stickerDao;
	
	public byte[] findStickerById(Integer stickerId) {
		Optional<Sticker> sticker = stickerDao.findById(stickerId);
		if(!sticker.isEmpty()) {
			return sticker.get().getStickerImg();
		}
		
		return null;
	}
	
}
