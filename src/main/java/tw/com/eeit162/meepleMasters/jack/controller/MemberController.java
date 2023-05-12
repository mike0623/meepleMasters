package tw.com.eeit162.meepleMasters.jack.controller;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Base64;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	 * 註冊會員
	 * @param body
	 * @return String
	 * @throws Exception 
	 * @throws ParseException 
	 */
	@ResponseBody
	@PostMapping("/createMember")
	public boolean createMember(@RequestBody String body) throws ParseException, Exception {
		JSONObject newMember = new JSONObject(body);
		Member email = mService.findMemberByEmail(newMember.getString("memberEmail")) ;
		if(email == null) {
			mService.createMember(body);
			return true;
		}
		
		return false;
	}
	
	@GetMapping("/auth/{token}")
	public String activeAccount(@PathVariable("token") String token) {
		mService.activeAccount(token);
		return "redirect:/login";
		
	}
	
	/**
	 * 會員登入
	 * @param member
	 * @param session
	 * @return Member
	 */
	@ResponseBody
	@PostMapping("/login")
	public String login(@RequestBody String member, HttpSession session) {
		
		Optional<Member> optional = mService.login(member);
		JSONObject url = new JSONObject();
		
		if (optional.isEmpty()) {
			// No Member
			url.put("url", "/login");
			return url.toString();
		}
		
		if(optional.get().getMemberLevel().contentEquals("管理員")) {
			session.setAttribute("member", optional.get());
			session.setMaxInactiveInterval(-1);
			System.out.println("管理員");
			url.put("url", "/member/admin");
			return url.toString();
		}else {
			if(optional.get().getMemberActive() == 1) {
				session.setAttribute("member", optional.get());
				session.setMaxInactiveInterval(-1);
				url.put("url","/index");
				return url.toString();
			}
		}
		url.put("url", "/login");
		return url.toString();
	}
	
	
	
	/**
	 * 登出
	 * @param session
	 * @return String
	 */
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
//		Member user = (Member) session.getAttribute("member");
		session.invalidate();
		
		return "redirect:/index";
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
	 * @throws ParseException 
	 * @throws JSONException 
	 */
	@ResponseBody
	@PutMapping("/member/updateMember/{id}")
	public String updateMember(@PathVariable(name = "id") Integer id, @RequestBody String body, HttpSession session) throws JSONException, ParseException {
		
		Integer memberUpdate = mService.updateMember(id, body);
		Member member = mService.findMemberById(id);
		System.out.println(member);
		if(memberUpdate!=0) {
			session.setAttribute("member", member);
			System.out.println("123");
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
	 * 確認email是否重複
	 * @param memberEmail
	 * @return Http status
	 */
	@ResponseBody
	@GetMapping("/checkMemberByEmail")
	public ResponseEntity<Member> checkMemberByEmail(@RequestParam("memberEmail") String memberEmail) {
		
		Member member = mService.findMemberByEmail(memberEmail);
		
		if(member != null) {
			System.out.println("ok");
			return new ResponseEntity<Member>(member, HttpStatus.OK);
		}
		
		System.out.println("no_content");
		return new ResponseEntity<Member>(member, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * 用Id找會員圖片
	 * @param memberId
	 * @return byte[]
	 */
	@ResponseBody
	@GetMapping("/member/findMemberImg/{id}")
	public ResponseEntity<byte[]> findMemberImg(@PathVariable("id") Integer memberId) {
		byte[] memberImg = mService.findMemberImg(memberId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		
		return new ResponseEntity<byte[]>(memberImg, headers, HttpStatus.OK);
	}
	
	/**
	 * 用Id找會員圖片
	 * @param memberId
	 * @return byte[]
	 */
	@ResponseBody
	@GetMapping("/member/emailFindMemberImg/{email}")
	public ResponseEntity<byte[]> findMemberImg(@PathVariable("email") String memberEmail) {
		byte[] memberImg = mService.findMemberImg(memberEmail);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		
		return new ResponseEntity<byte[]>(memberImg, headers, HttpStatus.OK);
	}
	
	
	/**
	 * 更新member圖片
	 * @param memberId
	 * @param memberImg
	 * @return ResponseEntity<byte[]>
	 */
	@ResponseBody
	@PutMapping("/member/updateMemberImg/{id}")
	public ResponseEntity<byte[]> updateImgById(@PathVariable("id") Integer memberId, @RequestBody String memberImg){
			
			  System.out.println(memberImg);
			
			  String encodedImg = memberImg.split(",")[1];
			  String imgString = encodedImg.replace("\"", "");
			  System.out.println(imgString);
			  byte[] decodedBytes = Base64.getDecoder().decode(imgString.getBytes(StandardCharsets.UTF_8));
			
			  mService.updateImg(memberId, decodedBytes);
			  HttpHeaders headers = new HttpHeaders();
			  headers.setContentType(MediaType.IMAGE_PNG);
			  return new ResponseEntity<byte[]>(decodedBytes, headers, HttpStatus.OK);
		
		
	}
	
	@ResponseBody
	@PostMapping("/member/updateMemberCoin")
	public Integer updateMemberCoin(@RequestBody String body) {
		JSONObject jsonObject = new JSONObject(body);
		int memberId = jsonObject.getInt("memberId");
		int coin = jsonObject.getInt("coin");
		return mService.updateMemberCoin(memberId, coin);
	}
	
	@ResponseBody
	@GetMapping("/member/findmemberByName")
	public Member findmemberByName(@RequestBody String body) {
		JSONObject jsonObject = new JSONObject(body);
		String name = jsonObject.getString("memberName");
		
		Optional<Member> member = mService.findmemberByName(name);
		
		if(member != null) {
			System.out.println(member.get().getMemberName());
			return member.get();
		}
		
		System.out.println("null");
		return null;
	}
}
