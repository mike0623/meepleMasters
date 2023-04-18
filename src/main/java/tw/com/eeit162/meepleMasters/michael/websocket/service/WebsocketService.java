package tw.com.eeit162.meepleMasters.michael.websocket.service;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;
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
		WebsocketUtil.putOnlineClient(session.getId(),this);
		System.out.print("用戶"+userEmail+"已連線");
		System.out.println("目前在線用戶數:"+WebsocketUtil.getOnlineClient().size());
		//以上為確認有啟動，不動他
		String onlineOfflineFriend = WebsocketUtil.getOnlineFriend(2); //取得在線好友與非在線好友
		WebsocketUtil.sendMessageByUserEmail(this.userEmail, onlineOfflineFriend); //跟自己的前端說目前在線跟離線好友有誰，用來渲染頁面
		//以下為上線時告訴其他在線好友我上線了
		JSONObject onOfflineJsonObject = new JSONObject(onlineOfflineFriend);
		
		System.out.println(onOfflineJsonObject.getJSONObject("onlineFriend").getString("AAA@gmail.com"));
		
		
		//以下測試程式
		
	}
	
	@OnMessage
    public void onMessage(String message,Session session) {
		System.out.println("用戶" + userEmail + "收到訊息: "+message);
		
		
		
		
		
		
		
		
		
		
		
//		try {
//			JSONObject jsonObject = new JSONObject(message);
//			if("sendMessageByUserEmail".equals(jsonObject.get("action"))) {
//				WebsocketUtil.sendMessageByUserEmail("Mike", jsonObject.getString("text"));
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	
	@OnError
    public void onError(Throwable t) {
		t.printStackTrace();
	}
	
	
	@OnClose
    public void onClose() {
		WebsocketUtil.moveOnlineClient(session.getId());
		System.out.print("用戶"+userEmail+"已離開");
		System.out.println("目前在線用戶:"+WebsocketUtil.getOnlineClient().size());
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

	@Override
	public String toString() {
		return "WebsocketService [session=" + session + ", userEmail=" + userEmail + "]";
	}
	
	
	
	
	
}
