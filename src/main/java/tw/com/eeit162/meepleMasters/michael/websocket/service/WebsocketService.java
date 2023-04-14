package tw.com.eeit162.meepleMasters.michael.websocket.service;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.michael.websocket.util.WebsocketUtil;


@Service
@ServerEndpoint("/michael/websocket/{userEmail}")
public class WebsocketService {
	
	private Session session;
	
	
	private  String userEmail;
	
	
	@OnOpen
	public void opOpen(@PathParam("userEmail") String userEmail,Session session) {
		this.userEmail = userEmail;
		this.session = session;
		WebsocketUtil.serviceClients.put(session.getId(),this);
		System.out.println("用戶"+session.getId()+"已連線");
	}
	
	@OnMessage
    public String onMessage(String message,Session session) {
		System.out.println("用戶" + session.getId() + "收到訊息");
		
		
		
		return this.userEmail + ": " + message;
	}
	
	
	@OnError
    public void onError(Throwable t) {
		t.printStackTrace();
	}
	
	
	@OnClose
    public void onClose() {
		WebsocketUtil.serviceClients.remove(session.getId());
		System.out.println("用戶"+session.getId()+"已離開");
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	
	
}
