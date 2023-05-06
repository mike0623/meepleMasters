package tw.com.eeit162.meepleMasters.michael.friend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.michael.friend.model.FriendInvite;
import tw.com.eeit162.meepleMasters.michael.friend.service.FriendInviteService;
import tw.com.eeit162.meepleMasters.michael.util.DataInterface;

@Controller
public class FriendInviteController {
	@Autowired
	private FriendInviteService inviteService;
	
	//邀請者發出邀請
	@PostMapping("/friendInvite/insert")
	@ResponseBody
	public void insertFriendInvite(@RequestBody String body) {
		JSONObject jsonObject = new JSONObject(body);
		Member myself = DataInterface.getMemberByEmail(jsonObject.getString("myself"));
		Member theOther = DataInterface.getMemberByEmail(jsonObject.getString("theOther"));
		
		
		FriendInvite friendInvite1 = inviteService.findFriendInviteByBoth(myself.getMemberId(),theOther.getMemberId());
		FriendInvite friendInvite2 = inviteService.findFriendInviteByBoth(theOther.getMemberId(),myself.getMemberId());
		if(friendInvite1==null && friendInvite2==null) {
			inviteService.insertFriendInvite(myself.getMemberId(),theOther.getMemberId());
		}else {
			System.out.println("資料已存在");
		}
	}
	
	//邀請者取消邀請 或受邀者拒絕邀請 受邀者接受邀請(放在friend裡)
	@PostMapping("/friendInvite/delete")
	@ResponseBody
	public void deleteFriendInvite(@RequestBody String body) {
		JSONObject jsonObject = new JSONObject(body);
		Member myself = DataInterface.getMemberByEmail(jsonObject.getString("myself"));
		Member theOther = DataInterface.getMemberByEmail(jsonObject.getString("theOther"));
		String whoIsAccepter = jsonObject.getString("accepter");
		if("myself".equals(whoIsAccepter)) {
			FriendInvite friendInvite = inviteService.findFriendInviteByBoth(theOther.getMemberId(),myself.getMemberId());
			if(friendInvite != null) {
				inviteService.deleteFriendInvite(friendInvite.getFriendInviteId());
			}
		}
		if("theOther".equals(whoIsAccepter)) {
			FriendInvite friendInvite = inviteService.findFriendInviteByBoth(myself.getMemberId(),theOther.getMemberId());
			if(friendInvite != null) {
				inviteService.deleteFriendInvite(friendInvite.getFriendInviteId());
			}
		}
	}
	
	
	
	//查看所有邀請
	@ResponseBody
	@GetMapping("/friendInvite/findByAccepterId/{AccepterEmail}")
	public String findFriendInviteByAccepterId(@PathVariable("AccepterEmail") String AccepterEmail){
		Integer AccepterId = DataInterface.getMemberByEmail(AccepterEmail).getMemberId();
		List<FriendInvite> list = inviteService.findFriendInviteByAccepterId(AccepterId);
		List<Member> InviterList = new ArrayList<Member>();
		for(FriendInvite friendInvite : list) {
			Member inviter = DataInterface.getMemberByMemberId(friendInvite.getFkInviterId());
			InviterList.add(inviter);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("InviterList", InviterList);
		return jsonObject.toString();
		
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
	//判斷是否存在邀請，回傳誰邀請誰
	@ResponseBody
	@GetMapping("/friendInvite/isExist/{myself}/{theOther}")
	public String isExist(@PathVariable("myself") String myself,@PathVariable("theOther") String theOther) {
		Integer myselfId = DataInterface.getMemberByEmail(myself).getMemberId();
		Integer theOtherId = DataInterface.getMemberByEmail(theOther).getMemberId();
		FriendInvite myselfIsInviter = inviteService.findFriendInviteByBoth(myselfId, theOtherId);
		JSONObject jsonObject = new JSONObject();
		if(myselfIsInviter != null) {
			jsonObject.put("inviter", myself);
			jsonObject.put("accepter", theOther);
			return jsonObject.toString();
		}
		FriendInvite theOtherIsInviter = inviteService.findFriendInviteByBoth(theOtherId, myselfId);
		if(theOtherIsInviter != null) {
			jsonObject.put("inviter", theOther);
			jsonObject.put("accepter", myself);
			return jsonObject.toString();
		}
		return null;
	}
	
}
