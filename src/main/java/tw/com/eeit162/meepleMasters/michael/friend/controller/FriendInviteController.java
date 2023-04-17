package tw.com.eeit162.meepleMasters.michael.friend.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.michael.friend.model.FriendInvite;
import tw.com.eeit162.meepleMasters.michael.friend.service.FriendInviteService;

@Controller
public class FriendInviteController {
	@Autowired
	private FriendInviteService inviteService;
	
	//邀請者發出邀請
	@PostMapping("/friendInvite/insert")
	public void insertFriendInvite(@RequestBody String body) {
		JSONObject jsonObject = new JSONObject(body);
		FriendInvite friendInvite1 = inviteService.findFriendInviteByBoth(jsonObject.getInt("inviterId"),jsonObject.getInt("accepterId"));
		FriendInvite friendInvite2 = inviteService.findFriendInviteByBoth(jsonObject.getInt("accepterId"),jsonObject.getInt("inviterId"));
		if(friendInvite1==null && friendInvite2==null) {
			inviteService.insertFriendInvite(jsonObject.getInt("inviterId"),jsonObject.getInt("accepterId"));
		}else {
			System.out.println("資料已存在");
		}
	}
	
	//邀請者取消邀請 受邀者接受邀請(放在friend裡)
	@PostMapping("/friendInvite/delete")
	public void deleteFriendInvite(@RequestBody String body) {
		JSONObject jsonObject = new JSONObject(body);
		FriendInvite friendInvite = inviteService.findFriendInviteByBoth(jsonObject.getInt("inviterId"),jsonObject.getInt("accepterId"));
		if(friendInvite != null) {
			inviteService.deleteFriendInvite(friendInvite.getFriendInviteId());
		}
	}
	
	//查看所有邀請
	@ResponseBody
	@GetMapping("/friendInvite/findByAccepterId/{AccepterId}")
	public List<FriendInvite> findFriendInviteByAccepterId(@PathVariable("AccepterId") Integer AccepterId){
		return inviteService.findFriendInviteByAccepterId(AccepterId);
	}
	
	//後臺管理者修改資料
	@PostMapping("/friendInvite/updateById/{id}")
	public void updateFriendInviteById(@PathVariable("id") Integer id,@RequestBody String body) {
		JSONObject jsonObject = new JSONObject(body);
		FriendInvite newFriendInvite = new FriendInvite();
		newFriendInvite.setFriendInviteId(id);
		newFriendInvite.setFkInviterId(jsonObject.getInt("inviterId"));
		newFriendInvite.setFkAccepterId(jsonObject.getInt("AccepterId"));
		FriendInvite oldFriendInvite = inviteService.findFriendInviteById(id);
		if(oldFriendInvite!=null) {
			inviteService.updateFriendInvite(newFriendInvite);
		}
	}
	
	
}
