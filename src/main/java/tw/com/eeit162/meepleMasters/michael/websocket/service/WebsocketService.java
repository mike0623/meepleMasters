package tw.com.eeit162.meepleMasters.michael.websocket.service;

import java.util.Iterator;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.michael.util.DataInterface;
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
		//-------------------------------------------------------------------------------------
		String onlineOfflineFriend = WebsocketUtil.getOnlineFriend(this.userEmail); //取得在線好友與非在線好友
		//以下為上線時告訴其他在線好友我上線了
		JSONObject onOfflineJsonObject = new JSONObject(onlineOfflineFriend);
		Iterator<String> onlineFriendEmail = onOfflineJsonObject.getJSONObject("onlineFriend").keys();
		JSONObject dataJson = new JSONObject();
		dataJson.put("action", "someoneLogin");
		dataJson.put("loginer", this.userEmail);
		while(onlineFriendEmail.hasNext()) {
			WebsocketUtil.sendMessageByUserEmail(onlineFriendEmail.next(),dataJson.toString());
		}
		//跟自己的前端說目前在線跟離線好友有誰，用來渲染頁面
		WebsocketUtil.sendMessageByUserEmail(this.userEmail, onOfflineJsonObject.toString()); 
		//-------------------------------------------------------------------------------------
		
		//以下測試程式
		
	}
	
	@OnMessage
    public void onMessage(String message,Session session) {
		System.out.println("用戶" + userEmail + "收到訊息: "+message);
		JSONObject json = new JSONObject(message);
		String action = json.getString("action");
		//-------------------------------------------------------------------------------------
		//接到前端傳來說我傳訊息給別人了，判斷對方是否在線，是否需要發訊息給他
		if("sendMessage".equals(action)) {
			boolean isOnline = false;
			Member receiver = DataInterface.getMemberByEmail(json.getString("receiver"));
			Member myself = DataInterface.getMemberByEmail(this.userEmail);
			for(WebsocketService service :WebsocketUtil.getOnlineClient().values()) {
				if(json.getString("receiver").equals(service.getUserEmail())) {
					Integer notRead = DataInterface.getNotRead(myself.getMemberId(),receiver.getMemberId());
					System.out.println("未讀訊息"+notRead);
					
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("action", "getMessage");
					jsonObject.put("notRead", notRead);
					jsonObject.put("senderName", myself.getMemberName());
					jsonObject.put("sender", this.userEmail);
					WebsocketUtil.sendMessageByUserEmail(json.getString("receiver"),jsonObject.toString());
					isOnline = true;
				}
			}
			//若不在線就更新排序，下次上線時才會跳出框框
			if(isOnline == false) {
				JSONObject sendToChatRoomOrder =  new JSONObject();
				sendToChatRoomOrder.put("fkOwner", receiver.getMemberId());
				sendToChatRoomOrder.put("fkChatToWhom", myself.getMemberId());
				sendToChatRoomOrder.put("chatOrderWhenLeave", 0);
				DataInterface.insertWhenSendToOfflineFriend(sendToChatRoomOrder.toString());
			}
		}
		//-------------------------------------------------------------------------------------
		//接到前端傳來說我要與對方解除好友關係
		if("deleteFriend".equals(action)) {
			String myself = json.getString("myself");
			String theOther = json.getString("theOther");
			for(WebsocketService service :WebsocketUtil.getOnlineClient().values()) {
				if(theOther.equals(service.getUserEmail())) {
					JSONObject deleteFriendJson =  new JSONObject();
					deleteFriendJson.put("action", "someoneDeleteFriendWithYou");
					deleteFriendJson.put("theOther", myself);
					WebsocketUtil.sendMessageByUserEmail(theOther, deleteFriendJson.toString());
				}
			}
		}
		//-------------------------------------------------------------------------------------
		//接到前端傳來說我要取消邀請
		if("deleteFriendInvite".equals(action)) {
			String myself = json.getString("myself");
			String theOther = json.getString("theOther");
			for(WebsocketService service :WebsocketUtil.getOnlineClient().values()) {
				if(theOther.equals(service.getUserEmail())) {
					JSONObject deleteFriendJson =  new JSONObject();
					deleteFriendJson.put("action", "someoneDeleteFriendInviteWithYou");
					deleteFriendJson.put("theOther", myself);
					WebsocketUtil.sendMessageByUserEmail(theOther, deleteFriendJson.toString());
				}
			}
		}
		//-------------------------------------------------------------------------------------
		//前端詢問該好友是否在線
		if("askIsOnflineFriend".equals(action)) {
			String myself = json.getString("myself");
			String theOther = json.getString("theOther");
			//回傳給自己要更新好友欄畫面
			boolean isOnline = WebsocketUtil.isOnline(theOther);
			JSONObject isOnlineJson = new JSONObject();
			isOnlineJson.put("action", "addFriend");
			isOnlineJson.put("newFriendEmail", theOther);
			String newFriendName = DataInterface.getMemberByEmail(theOther).getMemberName();
			isOnlineJson.put("newFriendName", newFriendName);
			isOnlineJson.put("isOnline", isOnline);
			WebsocketUtil.sendMessageByUserEmail(this.userEmail, isOnlineJson.toString());
			//如果對方在線，告訴對方我加他好友了
			for(WebsocketService service :WebsocketUtil.getOnlineClient().values()) {
				if(theOther.equals(service.getUserEmail())) {
					JSONObject confirmFriendInviteJson =  new JSONObject();
					confirmFriendInviteJson.put("action", "someoneConcirmFriendInvite");
					confirmFriendInviteJson.put("theOther", myself);
					String myselfName = DataInterface.getMemberByEmail(myself).getMemberName();
					confirmFriendInviteJson.put("theOtherName", myselfName);
					WebsocketUtil.sendMessageByUserEmail(theOther, confirmFriendInviteJson.toString());
				}
			}
		}
		//-------------------------------------------------------------------------------------
		//收到有人送好友邀請給我
		if("addFriendInvite".equals(action)) {
			String myself = json.getString("myself");
			String theOther = json.getString("theOther");
			for(WebsocketService service :WebsocketUtil.getOnlineClient().values()) {
				if(theOther.equals(service.getUserEmail())) {
					JSONObject deleteFriendJson =  new JSONObject();
					deleteFriendJson.put("action", "someoneAddFriendInviteToYou");
					deleteFriendJson.put("theOther", myself);
					WebsocketUtil.sendMessageByUserEmail(theOther, deleteFriendJson.toString());
				}
			}
		}
		//-------------------------------------------------------------------------------------
		//詢問在線好友已邀請加入遊戲
		if("getOnlineFriends".equals(action)) {
			String onlineOfflineFriend = WebsocketUtil.getOnlineFriend(this.userEmail); //取得在線好友與非在線好友
			JSONObject onOfflineJsonObject = new JSONObject(onlineOfflineFriend);
			Object object = onOfflineJsonObject.get("onlineFriend");
			JSONObject forGameInviteJson =  new JSONObject();
			forGameInviteJson.put("action", "onlineFriendForGameInvite");
			forGameInviteJson.put("onlineFriend", object);
			//跟自己的前端說目前在線好友有誰，用來渲染頁面
			WebsocketUtil.sendMessageByUserEmail(this.userEmail, forGameInviteJson.toString()); 
		}
		
		
		
		
		//-------------------------------------------------------------------------------------
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
		//-------------------------------------------------------------------------------------
		//離線時跟線上好友說我下線了
		String onlineOfflineFriend = WebsocketUtil.getOnlineFriend(this.userEmail); //取得在線好友與非在線好友
		if(!WebsocketUtil.isOnline(this.userEmail)) {  //判斷是否有多開網頁導致其中一個下線但其實還是在線
			JSONObject onOfflineJsonObject = new JSONObject(onlineOfflineFriend);
			Iterator<String> onlineFriendEmail = onOfflineJsonObject.getJSONObject("onlineFriend").keys();
			JSONObject dataJson = new JSONObject();
			dataJson.put("action", "someoneLogout");
			dataJson.put("logouter", this.userEmail);
			while(onlineFriendEmail.hasNext()) {
				WebsocketUtil.sendMessageByUserEmail(onlineFriendEmail.next(),dataJson.toString());
			}			
		}
		//-------------------------------------------------------------------------------------
		
		
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
