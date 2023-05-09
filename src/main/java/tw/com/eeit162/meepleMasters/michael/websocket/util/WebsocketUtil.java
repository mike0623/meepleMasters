package tw.com.eeit162.meepleMasters.michael.websocket.util;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.michael.util.DataInterface;
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
		Member member = DataInterface.getMemberByEmail(userEmail);
		Integer memberId = member.getMemberId();
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
				Member member2 =  DataInterface.getMemberByMemberId(resultJson.getJSONObject(i).getInt("fkMemberAId"));
				friendMap.put(member2.getMemberEmail(), member2.getMemberName());
			}
			if(resultJson.getJSONObject(i).getInt("fkMemberBId") != memberId) {
				Member member3 =  DataInterface.getMemberByMemberId(resultJson.getJSONObject(i).getInt("fkMemberBId"));
				friendMap.put(member3.getMemberEmail(), member3.getMemberName());
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
