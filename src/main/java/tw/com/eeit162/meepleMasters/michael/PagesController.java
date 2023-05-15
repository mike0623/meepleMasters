package tw.com.eeit162.meepleMasters.michael;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.FaveriteGameList;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.michael.util.DataInterface;

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
	public String playGameLobby(HttpSession session,Model model) {
		Member member = (Member)session.getAttribute("member");
		//增加我的最愛的modelAttr
		FaveriteGameList faveriteGameList = DataInterface.getFaveriteGameByEmail(member.getMemberEmail());
		List<Product> faveritelist = faveriteGameList.getFaveriteGameList();
		model.addAttribute("faveritelist", faveritelist);
		
		return "/michael/playGameLobby";
	}
	
	

	
}
