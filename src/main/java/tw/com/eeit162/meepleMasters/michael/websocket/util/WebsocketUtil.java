package tw.com.eeit162.meepleMasters.michael.websocket.util;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import tw.com.eeit162.meepleMasters.michael.websocket.service.WebsocketService;

public class WebsocketUtil {
	//在線用戶
	private static final Map<String,WebsocketService> ONLINE_CLIENT = new ConcurrentHashMap<String,WebsocketService>();
	
	//資料介接用
	private static RestTemplate template = new RestTemplate();
	
	
	public static Map<String, WebsocketService> getOnlineClient() {
		return ONLINE_CLIENT;
	}
	
	public static void putOnlineClient(String sessionId,WebsocketService ws) {
		ONLINE_CLIENT.put(sessionId, ws);
	}
	
	
	public static void moveOnlineClient(String sessionId) {
		ONLINE_CLIENT.remove(sessionId);
	}
	
	
	public static String getOnlineFriend(String userEmail) {
		boolean isOnlineFriend = false;
		JSONObject jsonObject = new JSONObject();
		//資料介接用email找memberId
		URI uri = URI.create("http://localhost:8080/meeple-masters/findMemberByEmail?memberEmail="+userEmail);
		RequestEntity<Void> requset = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<String> response = template.exchange(requset, String.class);
		JSONObject member = new JSONObject(response.getBody());
		Integer memberId = member.getInt("memberId");
//		System.out.println("自己的memberId:"+memberId);
		
		
		//資料介接friendController
		URI uri1 = URI.create("http://localhost:8080/meeple-masters/friend/selectByMemberId/"+memberId);
		RequestEntity<Void> requset1 = RequestEntity.get(uri1).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<String> response1 = template.exchange(requset1, String.class);
		JSONArray resultJson = new JSONArray(response1.getBody());
//		System.out.println("用自己的memberId去找到的自己的朋友:"+resultJson.toString());
		HashMap<String, String> friendMap = new HashMap<String, String>();
		for(int i = 0;i<resultJson.length();i++) {
			if(resultJson.getJSONObject(i).getInt("fkMemberAId") != memberId) {
				URI uri2 = URI.create("http://localhost:8080/meeple-masters/findMemberById?id="+resultJson.getJSONObject(i).getInt("fkMemberAId"));
				RequestEntity<Void> requset2 = RequestEntity.get(uri2).accept(MediaType.APPLICATION_JSON).build();
				ResponseEntity<String> response2 = template.exchange(requset2, String.class);
				JSONObject friendMember = new JSONObject(response2.getBody());
				friendMap.put(friendMember.getString("memberEmail"), friendMember.getString("memberName"));
			}
			if(resultJson.getJSONObject(i).getInt("fkMemberBId") != memberId) {
				URI uri2 = URI.create("http://localhost:8080/meeple-masters/findMemberById?id="+resultJson.getJSONObject(i).getInt("fkMemberBId"));
				RequestEntity<Void> requset2 = RequestEntity.get(uri2).accept(MediaType.APPLICATION_JSON).build();
				ResponseEntity<String> response2 = template.exchange(requset2, String.class);
				JSONObject friendMember = new JSONObject(response2.getBody());
				friendMap.put(friendMember.getString("memberEmail"), friendMember.getString("memberName"));
			}
		}
		HashMap<String,String> onlineFriend = new HashMap<String,String>();
		HashMap<String,String> offlineFriend = new HashMap<String,String>();
		for(String key : friendMap.keySet()) {
			isOnlineFriend=false;
			for(WebsocketService service : ONLINE_CLIENT.values()) {
//				System.out.println("印出在線玩家的email:"+service.getUserEmail());
//				System.out.println("印出map的key:"+key);
				if(service.getUserEmail().equals(key)) {
//					System.out.println("線上朋友+1");
					onlineFriend.put(key, friendMap.get(key));
					isOnlineFriend=true;
					continue;
				}
			}
			if(!isOnlineFriend) {
//				System.out.println("離線朋友+1");
				offlineFriend.put(key, friendMap.get(key));
			}
		}
		jsonObject.put("action","onOfflineFriend");
		jsonObject.put("onlineFriend", onlineFriend);
		jsonObject.put("offlineFriend", offlineFriend);
		
//		System.out.println("線上朋友數量:"+onlineFriend.size());
//		System.out.println("不在線朋友數量:"+offlineFriend.size());

		return jsonObject.toString();
	}
	
	public static boolean isOnline(String userEmail) {
		for(WebsocketService service : ONLINE_CLIENT.values()) {
			if(userEmail.equals(service.getUserEmail())) {
				return true;
			}
		}
		return false;
	}
	
	
	
	

	public static void sendMessageByUserEmail(String userEmail,String text) {
		for(WebsocketService service : ONLINE_CLIENT.values()) {
			if(userEmail.equals(service.getUserEmail()) && service.getSession() != null && service.getSession().isOpen()) {
				service.getSession().getAsyncRemote().sendText(text);
			}
		}
	}

	
	
	
	
}
