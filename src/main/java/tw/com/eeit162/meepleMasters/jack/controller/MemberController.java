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
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	
	/**
	 * 跳轉到註冊頁面
	 * @return createMember.jsp
	 */
	@RequestMapping("/member/createPage")
	public String createPage() {
		return "jack/createMember";
	}
	
	
	/**
	 * 註冊會員
	 * @param body
	 * @return String
	 * @throws IOException
	 */
	@ResponseBody
	@PostMapping("/member/createMember")
	public String createMember(@RequestBody String body) throws IOException {
		
		mService.createMember(body);
		
		return "jack/loginPage";
	}
	
//	public String memberAuth() {
//		
//		mService.memberAuth();
//		
//		return "";
//		
//	}
	
	@RequestMapping("/member/loginPageTest")
	public String loginPageTest() {
		return "jack/loginPage";
	}
	
	/**
	 * 會員登入
	 * @param member
	 * @param session
	 * @return Member
	 */
	@ResponseBody
	@PostMapping("/member/login")
	public String login(@RequestBody Member member, HttpSession session) {
		
		Optional<Member> optional = mService.login(member);
		
		if (optional.isEmpty()) {
			// No Member
			return null;
		}
		session.setAttribute("member", optional.get());
		if(optional.get().getMemberLevel().contentEquals("管理員")) {
			System.out.println("管理員");
			return ""; 
		}
			
		return "";
	}
	
	/**
	 * 登出
	 * @param session
	 * @return String
	 */
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "Logout";
	}
	
	/**
	 * 更新密碼
	 * @param memberId
	 * @param memberPwd
	 * @return String
	 */
	@ResponseBody
	@PutMapping("/member/updatePwd/{id}")
	public String updatePwd(@PathVariable(name = "id")Integer memberId, @RequestBody String memberPwd) {
		
		
		Integer update = mService.updatePwd(memberId, memberPwd);
		System.out.println(memberPwd);
		if(update!=0) {
			
			return "success";
		}
		
		return "fail";
	}
	
	/**
	 * 更改Member資料
	 * @param id
	 * @param body
	 * @return Member
	 */
	@ResponseBody
	@PutMapping("/member/updateMember/{id}")
	public String updateMember(@PathVariable(name = "id") Integer id, @RequestBody String body) {
		
		Integer memberUpdate = mService.updateMember(id, body);
		if(memberUpdate!=0) {
			
			return "success";
		}
		
		return "fail";
	}
	
	/**
	 * 用Id找會員
	 * @param id
	 * @return Member(Json)
	 */
	@ResponseBody
	@GetMapping("/member/findMemberById")
	public Member findMemberById(@RequestParam("id") Integer id) {
		
		Member member = mService.findMemberById(id);
		
		return member;
	}
	
	/**
	 * 用Email找會員
	 * @param memberEmail
	 * @return Member(Json)
	 */
	@ResponseBody
	@GetMapping("/member/findMemberByEmail")
	public Member findMemberByEmail(@RequestParam("memberEmail") String memberEmail) {
		
		Member member = mService.findMemberByEmail(memberEmail);
		
		return member;
	}
	
	
	/**
	 * 用Id找會員圖片
	 * @param memberId
	 * @return byte[]
	 */
	@GetMapping("/member/findMemberImg/{id}")
	@ResponseBody
	public byte[] findMemberImg(@PathVariable("id") Integer memberId) {
		
		return mService.findMemberImg(memberId);
	}
	
}
