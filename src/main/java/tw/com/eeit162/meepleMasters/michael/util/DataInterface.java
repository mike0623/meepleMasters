package tw.com.eeit162.meepleMasters.michael.util;

import java.net.URI;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;

public class DataInterface {
	
	//資料介接用
	private static RestTemplate template = new RestTemplate();
	
	
	public static Member getMemberByMemberId(Integer memberId) {
		Member member = new Member();
		
		URI uri = URI.create("http://localhost:8080/meeple-masters/member/findMemberById?id="+memberId);
		RequestEntity<Void> requset = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<String> response = template.exchange(requset, String.class);
		JSONObject memberJson = new JSONObject(response.getBody());
		
		member.setMemberId(memberJson.getInt("memberId"));
		member.setMemberEmail(memberJson.getString("memberEmail"));
		member.setMemberPwd(memberJson.getString("memberPwd"));
		member.setMemberName(memberJson.getString("memberName"));
		member.setMemberAge(memberJson.getInt("memberAge"));
		member.setMemberGender(memberJson.getString("memberGender"));
		member.setMemberTel(memberJson.getString("memberTel"));
		member.setMemberAddress(memberJson.getString("memberAddress"));
		
		return member;
	}
	
	public static Member getMemberByEmail(String memberEmail) {
		Member member = new Member();
		
		URI uri = URI.create("http://localhost:8080/meeple-masters/member/findMemberByEmail?memberEmail="+memberEmail);
		RequestEntity<Void> requset = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<String> response = template.exchange(requset, String.class);
		JSONObject memberJson = new JSONObject(response.getBody());
		
		member.setMemberId(memberJson.getInt("memberId"));
		member.setMemberEmail(memberJson.getString("memberEmail"));
		member.setMemberPwd(memberJson.getString("memberPwd"));
		member.setMemberName(memberJson.getString("memberName"));
		member.setMemberAge(memberJson.getInt("memberAge"));
		member.setMemberGender(memberJson.getString("memberGender"));
		member.setMemberTel(memberJson.getString("memberTel"));
		member.setMemberAddress(memberJson.getString("memberAddress"));
		
		
		return member;
	}
	
	public static Integer getNotRead(Integer senderId,Integer receiverId) {
		URI uri = URI.create("http://localhost:8080/meeple-masters/friendMessage/findNotRead?senderId="+senderId+"&receiverId="+receiverId);
		RequestEntity<Void> requset = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Integer> response = template.exchange(requset, Integer.class);
		return response.getBody();
	}
	
	public static Integer getMessageTextNewId(String messageText) {
		URI uri = URI.create("http://localhost:8080/meeple-masters/messageText/insertMessageText");
		RequestEntity<String> requset = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).header("Content-Type", "application/json;charset=UTF-8").body(messageText);
		ResponseEntity<Integer> response = template.exchange(requset, Integer.class);
		return response.getBody();
	}
	
	public static Integer getMessageStickerNewId(Integer messageSticker) {
		URI uri = URI.create("http://localhost:8080/meeple-masters/messageSticker/insertMessageSticker");
		RequestEntity<Integer> requset = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).header("Content-Type", "application/json;charset=UTF-8").body(messageSticker);
		ResponseEntity<Integer> response = template.exchange(requset, Integer.class);
		return response.getBody();
	}
	
	
	public static Integer getMessageStickerFkStickerIdById(Integer messageStickerId) {
		URI uri = URI.create("http://localhost:8080/meeple-masters/messageSticker/findById/"+messageStickerId);
		RequestEntity<Void> requset = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Integer> response = template.exchange(requset, Integer.class);
		return response.getBody();
	}
	
	public static void insertWhenSendToOfflineFriend(String json) {
		URI uri = URI.create("http://localhost:8080/meeple-masters/chatRoomOrder/insertWhenSendToOfflineFriend");
		RequestEntity<String> requset = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).header("Content-Type", "application/json;charset=UTF-8").body(json);
		ResponseEntity<Void> response = template.exchange(requset, Void.class);
	}
}
