package tw.com.eeit162.meepleMasters.michael.friend.controller;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.michael.friend.model.Friend;
import tw.com.eeit162.meepleMasters.michael.friend.model.FriendInvite;
import tw.com.eeit162.meepleMasters.michael.friend.service.FriendInviteService;
import tw.com.eeit162.meepleMasters.michael.friend.service.FriendService;
import tw.com.eeit162.meepleMasters.michael.util.DateTimeConverter;

@Controller
public class FriendController {
	@Autowired
	private FriendService friendService;
	@Autowired
	private FriendInviteService inviteService;


//	@GetMapping("/friend/")
//	public void processAction() {
//		friendService.insertFriend();
//	}
	
	
	//按下接受邀請按鈕後 
	@PostMapping("/friend/acceptInvite")
	public void acceptInvite(@RequestBody String body) {
		JSONObject jsonObject = new JSONObject(body);

		FriendInvite friendInvite = inviteService.findFriendInviteByBoth(jsonObject.getInt("inviterId"),jsonObject.getInt("accepterId"));
		if(friendInvite != null) {
			friendService.insertFriend(jsonObject.getInt("inviterId"),jsonObject.getInt("accepterId"));
			inviteService.deleteFriendInvite(friendInvite.getFriendInviteId());
		}
	}
	
	//刪除好友
	@PostMapping("/friend/delete")
	public void deleteFriend(@RequestBody String body) {
		JSONObject jsonObject = new JSONObject(body);
		Friend result1 = friendService.findFriendByBoth(jsonObject.getInt("memberAId"), jsonObject.getInt("memberBId"));
		
		if(friendService.isFriend(jsonObject.getInt("memberAId"), jsonObject.getInt("memberBId"))) {
			//如果result1不存在，就代表反過來查詢存在
			Integer id = result1==null?friendService.findFriendByBoth(jsonObject.getInt("memberBId"), jsonObject.getInt("memberAId")).getFriendId():result1.getFriendId();
			friendService.deleteFriendById(id);
		}
	}
	
	//後臺直接管理
	@PostMapping("/friend/update")
	public void updateFriend(@RequestBody String body) {
		JSONObject jsonObject = new JSONObject(body);
		Friend friend = new Friend();
		friend.setFriendId(jsonObject.getInt("id"));
		friend.setFkMemberAId(jsonObject.getInt("memberAId"));
		friend.setFkMemberBId(jsonObject.getInt("memberBId"));
		//日期可不給，若有給一定要輸入對的格式，不然就會變成現在時間
		if(!jsonObject.isNull("createdDate")) {
			Date updatedDate = DateTimeConverter.parse(jsonObject.get("createdDate").toString(), "yyyy-MM-dd HH:mm:ss");
			friend.setFriendCreatedDate(updatedDate);
		}
		
		friendService.updateFriend(friend);
	}
	
	//查詢所有好友
	@ResponseBody
	@GetMapping("/friend/selectByMemberId/{memberId}")
	public List<Friend> selectFriendByMemberId(@PathVariable("memberId") Integer memberId){
		return friendService.findFriendByMemberId(memberId);
	}
	
	
	
	
}
