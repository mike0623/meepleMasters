package tw.com.eeit162.meepleMasters.michael;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.michael.game.Game;
import tw.com.eeit162.meepleMasters.michael.game.bridge.Bridge;
import tw.com.eeit162.meepleMasters.michael.game.room.GameRoomUtil;

@Controller
public class PagesController {

	@GetMapping("/michael/index")
	public String processAction() {
		return "michael/michael";
	}
	
	
	
	
	@GetMapping("/michael/testWebsocket")
	public String testWebsocket(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member member = new Member();
		member.setMemberEmail(request.getParameter("userEmail"));
		member.setMemberPwd("1234");
		session.setAttribute("member", member);
		return "michael/michael";
	}
	
	@GetMapping("/michael/testIndex")
	public String testIndex() {
		return "michael/testIndex";
	}
	
	
	@GetMapping("/michael/friendList")
	public String friendList() {
		return "include/friend/friendList";
	}
	
	//跳轉到遊戲大廳
	@GetMapping("/game/playGameLobby")
	public String playGameLobby() {
		//增加我的最愛的modelAttr
		
		return "/michael/playGameLobby";
	}
	
	

	
}
