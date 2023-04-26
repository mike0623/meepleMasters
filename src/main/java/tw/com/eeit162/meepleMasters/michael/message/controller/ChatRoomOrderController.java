package tw.com.eeit162.meepleMasters.michael.message.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
import tw.com.eeit162.meepleMasters.michael.message.model.ChatRoomOrder;
import tw.com.eeit162.meepleMasters.michael.message.model.MessageDto;
import tw.com.eeit162.meepleMasters.michael.message.service.ChatRoomOrderService;
import tw.com.eeit162.meepleMasters.michael.util.DataInterface;

@Controller
public class ChatRoomOrderController {
	
	@Autowired
	private ChatRoomOrderService orderService;
	
	
	//資料介接用
	private static RestTemplate template = new RestTemplate();
	
	@PostMapping("/chatRoomOrder/reflesh")
	public ResponseEntity<Void> refleshchatRoomOrder(@RequestBody String json) {
		try {
		JSONObject body = new JSONObject(json);
		//取得owner的memberId
		String owner = body.getString("owner");
		URI uri = URI.create("http://localhost:8080/meeple-masters/findMemberByEmail?memberEmail="+owner);
		RequestEntity<Void> requset = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<String> response = template.exchange(requset, String.class);
		JSONObject member = new JSONObject(response.getBody());
		Integer ownerId = member.getInt("memberId");
		//刪除原本的排序資料
		orderService.deleteChatRoomOrderByOwnerId(ownerId);
		
		
		//新增新的排序資料
		JSONArray chatToWhom = body.getJSONArray("chatToWhom");
		JSONArray chatOrder = body.getJSONArray("chatOrder");
		if(chatToWhom.length()>0) {
			for(int i = 0;i<chatToWhom.length();i++) {
				ChatRoomOrder chatRoomOrder = new ChatRoomOrder();
				URI uri1 = URI.create("http://localhost:8080/meeple-masters/findMemberByEmail?memberEmail="+chatToWhom.getString(i));
				RequestEntity<Void> requset1 = RequestEntity.get(uri1).accept(MediaType.APPLICATION_JSON).build();
				ResponseEntity<String> response1 = template.exchange(requset1, String.class);
				JSONObject member1 = new JSONObject(response1.getBody());
				Integer fkChatToWhom = member1.getInt("memberId");
				
				chatRoomOrder.setFkOwner(ownerId);
				chatRoomOrder.setFkChatToWhom(fkChatToWhom);
				chatRoomOrder.setChatOrderWhenLeave(chatOrder.getInt(i));
				orderService.insertChatRoomOrder(chatRoomOrder);
			}
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ResponseBody
	@GetMapping("/chatRoomOrder/findByFkOwner")
	public List<MessageDto> findByFkOwner(@RequestParam("owner") String owner){
		Integer ownerId = DataInterface.getMemberByEmail(owner).getMemberId();
		
		List<ChatRoomOrder> chatRoomOrderList = orderService.findByFkOwner(ownerId);
		List<MessageDto> list = new ArrayList<MessageDto>();
		for(ChatRoomOrder chat : chatRoomOrderList) {
			MessageDto dto = new MessageDto();
			Member member = DataInterface.getMemberByMemberId(chat.getFkChatToWhom());
			dto.setChatToWhom(member.getMemberEmail());
			dto.setChatOrderWhenLeave(chat.getChatOrderWhenLeave());
			dto.setCreatedDate(chat.getCreatedDate());
			dto.setNotRead(DataInterface.getNotRead(chat.getFkChatToWhom(), ownerId));
			dto.setChatToWhomName(member.getMemberName());
			
			list.add(dto);
		}
		return list;
	}
}
