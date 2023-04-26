package tw.com.eeit162.meepleMasters.michael.message.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.michael.message.model.FriendMessage;
import tw.com.eeit162.meepleMasters.michael.message.model.MessageDto;
import tw.com.eeit162.meepleMasters.michael.message.service.FriendMessageService;
import tw.com.eeit162.meepleMasters.michael.util.DataInterface;

@Controller
public class FriendMessageController {

	@Autowired
	private FriendMessageService messageService;

	// 資料介接用
	private static RestTemplate template = new RestTemplate();

	@PostMapping("/friendMessage/insertFriendMessage")
	@ResponseBody
	public void insertFriendMessage(@RequestBody String json) {
		FriendMessage friendMessage = new FriendMessage();
		JSONObject body = new JSONObject(json);
		Integer senderId = DataInterface.getMemberByEmail(body.getString("sender")).getMemberId();
		Integer receiverId = DataInterface.getMemberByEmail(body.getString("receiver")).getMemberId();
		Integer messageType = body.getInt("messageType");

		if(messageType == 1) {
			//做文字的事情
			String messageText = body.getString("messageText");
			Integer messageTextNewId = DataInterface.getMessageTextNewId(messageText);
			friendMessage.setFkMessageContent(messageTextNewId);
		}
		if(messageType == 2) {
			//做貼圖的事情
			Integer messageSticker = body.getInt("messageSticker");
			Integer messageStickerNewId = DataInterface.getMessageStickerNewId(messageSticker);
			friendMessage.setFkMessageContent(messageStickerNewId);
		}
		friendMessage.setFkSenderId(senderId);
		friendMessage.setFkReceiverId(receiverId);
		friendMessage.setMessageType(messageType);
		friendMessage.setHaveRead(0);
		
		
		messageService.insertFriendMessage(friendMessage);
	}

	@PostMapping("/friendMessage/updateHaveRead")
	public ResponseEntity<Void> updateHaveRead(@RequestBody String json) {
		JSONObject body = new JSONObject(json);
		String sender = body.getString("sender");
		String receiver = body.getString("receiver");
		RequestEntity<Void> request1 = RequestEntity
				.get("http://localhost:8080/meeple-masters/findMemberByEmail?memberEmail=" + sender)
				.accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<String> response1 = template.exchange(request1, String.class);
		JSONObject sendMember = new JSONObject(response1.getBody());
		int sendId = sendMember.getInt("memberId");

		RequestEntity<Void> request2 = RequestEntity
				.get("http://localhost:8080/meeple-masters/findMemberByEmail?memberEmail=" + receiver)
				.accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<String> response2 = template.exchange(request2, String.class);
		JSONObject receiverMember = new JSONObject(response2.getBody());
		int receiverId = receiverMember.getInt("memberId");

		messageService.updateHaveRead(sendId, receiverId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/friendMessage/findPersonalMessage")
	@ResponseBody
	public List<MessageDto> findPersonalMessage(@RequestParam("sender") String sender,
			@RequestParam("receiver") String receiver) {
		RequestEntity<Void> request1 = RequestEntity
				.get("http://localhost:8080/meeple-masters/findMemberByEmail?memberEmail=" + sender)
				.accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<String> response1 = template.exchange(request1, String.class);
		JSONObject sendMember = new JSONObject(response1.getBody());
		int sendId = sendMember.getInt("memberId");

		RequestEntity<Void> request2 = RequestEntity
				.get("http://localhost:8080/meeple-masters/findMemberByEmail?memberEmail=" + receiver)
				.accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<String> response2 = template.exchange(request2, String.class);
		JSONObject receiverMember = new JSONObject(response2.getBody());
		int receiverId = receiverMember.getInt("memberId");

		List<FriendMessage> senderIsSender = messageService.findAllBySenderAndReceiver(sendId, receiverId);
		List<MessageDto> dtoList = new ArrayList<MessageDto>();
		
		for (FriendMessage message : senderIsSender) {
			MessageDto messageDto = new MessageDto();
			if (1 == message.getMessageType()) {
				// 查詢文字是什麼
				RequestEntity<Void> request3 = RequestEntity.get(
						"http://localhost:8080/meeple-masters/messageText/findById/" + message.getFkMessageContent())
						.accept(MediaType.APPLICATION_JSON).build();
				ResponseEntity<String> response3 = template.exchange(request3, String.class);
				messageDto.setText(response3.getBody());
			} else {
				// 查詢貼圖是什麼
				Integer fkStickerId = DataInterface.getMessageStickerFkStickerIdById(message.getFkMessageContent());
				messageDto.setStickerId(fkStickerId);
			}
			if(sendId == message.getFkSenderId()) {
				messageDto.setSender(sender);
				messageDto.setReceiver(receiver);
			}
			if(sendId == message.getFkReceiverId()) {
				messageDto.setSender(receiver);
				messageDto.setReceiver(sender);
			}
			messageDto.setCreatedDate(message.getFriendMessageCreatedDate());

			dtoList.add(messageDto);
		}

		return dtoList;
	}
	
	@GetMapping("/friendMessage/findNotRead")
	@ResponseBody
	public Integer findNotRead(@RequestParam("senderId") Integer senderId,@RequestParam("receiverId") Integer receiverId) {
		return messageService.findNotRead(senderId, receiverId);
	}

}
