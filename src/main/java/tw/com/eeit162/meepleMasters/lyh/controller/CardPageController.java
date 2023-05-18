package tw.com.eeit162.meepleMasters.lyh.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jack.model.dao.MemberDao;

@Controller
public class CardPageController {
	
	@Autowired
	private MemberDao mDao;

	@GetMapping("/cardList")
	public String cardList() {
		return "lyh/cardList";
	}
	
	@GetMapping("/mycard")
	public String mycard() {
		return "lyh/mycard";
	}
	
	@GetMapping("/card/releasedList")
	public String cardReleased(HttpSession session) {
		
		Member member = (Member)session.getAttribute("member");
		Optional<Member> op = mDao.findById(member.getMemberId());
		
		if (op.isPresent()) {
			session.setAttribute("member", op.get());
		}
		
		return "lyh/cardReleased";
	}
	
	@GetMapping("/newRelease")
	public String newCardReleased() {
		return "lyh/newCardRelease";
	}
	
	@GetMapping("/editRelease")
	public String editCardReleased() {
		return "lyh/editCardRelease";
	}
	  
	@GetMapping("/newAuction")
	public String newCardAuction() {
		return "lyh/newCardAuction";
	}
	
	@GetMapping("/editAuction")
	public String editCardAuction() {
		return "lyh/editCardAuction";
	}
	
}
