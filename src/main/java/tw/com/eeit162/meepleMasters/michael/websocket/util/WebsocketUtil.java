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
	
	
	public static String getOnlineFriend(Integer memberId) {
		boolean isOnlineFriend = false;
		JSONObject jsonObject = new JSONObject();
		URI uri = URI.create("http://localhost:8080/meeple-masters/friend/selectByMemberId/"+memberId);
		RequestEntity<Void> requset = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<String> response = template.exchange(requset, String.class);
		String body = response.getBody();
		JSONObject resultJson = new JSONObject(body);
		JSONArray jsonArray = resultJson.getJSONArray("friendName");
		HashMap<String,String> onlineFriend = new HashMap<String,String>();
		HashMap<String,String> offlineFriend = new HashMap<String,String>();
		for(int i = 0;i<jsonArray.length();i++) {
			isOnlineFriend=false;
			for(WebsocketService service : ONLINE_CLIENT.values()) {
				if(service.getUserEmail().equals(jsonArray.getJSONObject(i).get("memberEmail").toString())) {
					onlineFriend.put(jsonArray.getJSONObject(i).get("memberEmail").toString(), jsonArray.getJSONObject(i).get("memberName").toString());
					isOnlineFriend=true;
					continue;
				}
			}
			if(!isOnlineFriend) {
				offlineFriend.put(jsonArray.getJSONObject(i).get("memberEmail").toString(), jsonArray.getJSONObject(i).get("memberName").toString());
			}
		}
		jsonObject.put("onlineFriend", onlineFriend);
		jsonObject.put("offlineFriend", offlineFriend);
		
//		System.out.println("線上朋友數量:"+onlineFriend.size());
//		System.out.println("不在線朋友數量:"+offlineFriend.size());
		
		
		
		return jsonObject.toString();
	}
	
	
	
	

	public static void sendMessageByUserEmail(String userEmail,String text) {
		for(WebsocketService service : ONLINE_CLIENT.values()) {
			if(userEmail.equals(service.getUserEmail()) && service.getSession() != null && service.getSession().isOpen()) {
				service.getSession().getAsyncRemote().sendText(text);
			}
		}
	}

	
	
	
	
}
