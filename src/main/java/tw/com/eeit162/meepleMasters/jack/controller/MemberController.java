package tw.com.eeit162.meepleMasters.jack.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jack.mail.JavaMail;
import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jack.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	private MemberService mService;

	@GetMapping("/test123")
	public String test() {
		mService.testMember(null);
		return "jack/jack";
	}
	
	@PostMapping("/createMember")
	public String createMember(@RequestBody String body) throws IOException {
		
		mService.createMember(body);
		
		return "";
	}
	
	@ResponseBody
	@PostMapping("/login")
	public Member login(@RequestBody Member member, HttpSession session) {
		
		Optional<Member> optional = mService.login(member);
		if (optional.isEmpty()) {
			// No Member
			return null;
		}
		Member mLogin = optional.get();
		session.setAttribute("member", mLogin);
		
		return mLogin;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "Logout";
	}
	
	@PutMapping("/updateMember/{id}")
	public Member modify(@PathVariable(name = "id") Integer id, @RequestBody String body, HttpSession session) {
		
		Member memberUpdate = mService.modify(body, session);
		
		return memberUpdate;
	}
	
	@ResponseBody
	@PutMapping("/updatePwd/{id}")
	public String updatePwd(@PathVariable(name = "id")Integer memberId, @RequestBody String memberPwd) {
		
		
		Integer update = mService.updatePwd(memberId, memberPwd);
		System.out.println(memberPwd);
		if(update!=0) {
			
			return "success";
		}
		
		return "fail";
	}
	
	@ResponseBody
	@GetMapping("/findMemberById")
	public Member findMemberById(@RequestParam("id") Integer id) {
		
		Member member = mService.findMemberById(id);
		
		return member;
	}
	
	@ResponseBody
	@GetMapping("/findMemberByEmail")
	public Member findMemberByEmail(@RequestParam("memberEmail") String memberEmail) {
		
		Member member = mService.findMemberByEmail(memberEmail);
		
		return member;
	}
	
}
