package tw.com.eeit162.meepleMasters.michael.message.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.michael.message.model.ChatRoomOrder;
import tw.com.eeit162.meepleMasters.michael.message.model.MessageDto;
import tw.com.eeit162.meepleMasters.michael.message.service.ChatRoomOrderService;
import tw.com.eeit162.meepleMasters.michael.util.DataInterface;

@Controller
public class ChatRoomOrderController {
	
	@Autowired
	private ChatRoomOrderService orderService;

	
	@PostMapping("/chatRoomOrder/reflesh")
	public ResponseEntity<Void> refleshchatRoomOrder(@RequestBody String json) {
		try {
		JSONObject body = new JSONObject(json);
		//取得owner的memberId
		String owner = body.getString("owner");
		
		Integer ownerId = DataInterface.getMemberByEmail(owner).getMemberId();
		//刪除原本的排序資料
		orderService.deleteChatRoomOrderByOwnerId(ownerId);
		
		
		//新增新的排序資料
		JSONArray chatToWhom = body.getJSONArray("chatToWhom");
		JSONArray chatOrder = body.getJSONArray("chatOrder");
		if(chatToWhom.length()>0) {
			for(int i = 0;i<chatToWhom.length();i++) {
				ChatRoomOrder chatRoomOrder = new ChatRoomOrder();
				Integer fkChatToWhom = DataInterface.getMemberByEmail(chatToWhom.getString(i)).getMemberId();
				
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
	
	
	@PostMapping("/chatRoomOrder/insertWhenSendToOfflineFriend")
	@ResponseBody
	public String insertWhenSendToOfflineFriend(@RequestBody String json) {
		JSONObject body = new JSONObject(json);
		Integer fkOwner = body.getInt("fkOwner");
		Integer fkChatToWhom = body.getInt("fkChatToWhom");
		Integer chatOrderWhenLeave = body.getInt("chatOrderWhenLeave");
		//如果本來存在就刪掉原本的
		if(orderService.isExist(fkOwner, fkChatToWhom)) {
			orderService.deleteByBoth(fkOwner, fkChatToWhom);
		}
		
		ChatRoomOrder chatRoomOrder = new ChatRoomOrder();
		
		chatRoomOrder.setFkOwner(fkOwner);
		chatRoomOrder.setFkChatToWhom(fkChatToWhom);
		chatRoomOrder.setChatOrderWhenLeave(chatOrderWhenLeave);
		
		
		orderService.insertChatRoomOrder(chatRoomOrder);
		return "新增成功";
	}
	
	
	@GetMapping("/chatRoomOrder/test")
	@ResponseBody
	public void test() {
		System.out.println(123123);
	}

}
