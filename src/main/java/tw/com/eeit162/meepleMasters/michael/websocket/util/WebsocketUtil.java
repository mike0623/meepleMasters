package tw.com.eeit162.meepleMasters.michael.websocket.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import tw.com.eeit162.meepleMasters.michael.websocket.service.WebsocketService;

public class WebsocketUtil {
	//在線用戶
	public static final Map<String,WebsocketService> serviceClients = new ConcurrentHashMap<String,WebsocketService>();
	
	public static void sendMessageByUserEmail(String userEmail,String text) {
		for(WebsocketService service : serviceClients.values()) {
			if(userEmail.equals(service.getUserEmail()) && service.getSession() != null && service.getSession().isOpen()) {
				service.getSession().getAsyncRemote().sendText(text);
			}
		}
	}
}
