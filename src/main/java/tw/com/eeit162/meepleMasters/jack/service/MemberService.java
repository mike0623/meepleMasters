package tw.com.eeit162.meepleMasters.jack.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import tw.com.eeit162.meepleMasters.jack.mail.JavaMail;
import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jack.model.dao.MemberDao;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private MailService mailService;

	public void testMember(Member member) {
		Member member1 = new Member();
		member1.setMemberEmail("123@gmail.com");
		member1.setMemberPwd("1234");

		memberDao.save(member1);

	}

	public Member createMember(String json) throws IOException {
		JSONObject jObject = new JSONObject(json);

		String email = jObject.getString("memberEmail");
		String password = jObject.getString("memberPwd");
		String name = jObject.getString("memberName");
		Integer age = jObject.isNull("memberAge") ? null : jObject.getInt("memberAge");
		String gender = jObject.isNull("memberGender") ? null : jObject.getString("memberGender");
		String tel = jObject.isNull("memberTel") ? null : jObject.getString("memberTel");
		String address = jObject.isNull("memberAddress") ? null : jObject.getString("memberAddress");

		Member member = new Member();
		member.setMemberEmail(email);
		member.setMemberPwd(password);
		member.setMemberName(name);
		member.setMemberLevel("一般會員");
		member.setMemberAge(age);
		member.setMemberGender(gender);
		member.setMemberTel(tel);
		member.setMemberAddress(address);
		member.setMemberCoin(500);
		member.setMemberActive(0);
		member.setCreateTime(new Date());

		File file = new File("");
		String filePath = file.getAbsolutePath();
		FileInputStream fis = new FileInputStream(new File(filePath + "\\src\\main\\webapp\\img\\user.png"));
		BufferedInputStream bis = new BufferedInputStream(fis);

		member.setMemberImg(bis.readAllBytes());

		bis.close();
		fis.close();
		
		memberDao.save(member);
		
		sendVerificationEmail(member);
		
		return member;
	}
	
	public void sendVerificationEmail(Member member) {
		System.out.println("已寄出");
		String token = jwtService.generateToken(member);
		String subject = "MeepleMasters會員信箱驗證";
		String url = "http://localhost:8080/meeple-masters/auth/"+token;
		String content = "您好! 歡迎您加入Meeple Masters，<br/>" + 
				 "這封信是由Meeple Masters的會員註冊系統所寄出，<br/>" + 
				 "<h4>請點選下面的連結來進行註冊的下一個步驟：<br/></h4>" + 
				 "<h3><a href=\"" + url + "\">"+"驗證連結"+"</a></h3>"+ "<br/>" + 
				 "※ 如果您無法連結信中網址，請讓我們知道。<br/>"+ 
				 "<br/>" + 
				 "Meeple Masters團隊 敬上<br/>";
		mailService.sendEmail(member.getMemberEmail(), subject, content);
	}
	
	public void activeAccount(String token) {
		if(jwtService.validateToken(token)) {
			String email = jwtService.extractEmail(token);
			Member member = findMemberByEmail(email);
			if(member!=null) {
				member.setMemberActive(1);
				memberDao.save(member);
			}
		}
	}



	public Optional<Member> login(String json) {
		JSONObject member = new JSONObject(json);
		String email = member.getString("memberEmail");
		String password = member.getString("memberPwd");

		Optional<Member> option = Optional.ofNullable(memberDao.findMemberByEmail(email));
		if (option.isEmpty()) {
			return Optional.empty();
		} else {
			String memberPwd = option.get().getMemberPwd();
			if (password.equals(memberPwd)) {
				return option;
			}
			return Optional.empty();
		}

//		Member memberLogin = memberDao.findMemberByEmailandPassword(member.getMemberEmail(), member.getMemberPwd());
//		return Optional.ofNullable(memberDao.findMemberByEmailandPassword(member.getMemberEmail(), member.getMemberPwd())); 
	}

	public Integer updatePwd(Integer id, String json) {
		JSONObject jObject = new JSONObject(json);

//		Integer id = jObject.isNull("memberId")?null:jObject.getInt("memberId");
		String password = jObject.isNull("memberPwd") ? null : jObject.getString("memberPwd");

		return memberDao.updatePasswordById(id, password);

	}
	
	public Integer updateMember(Integer id, String json) {
		JSONObject jObject = new JSONObject(json);

		Optional<Member> memberData = memberDao.findById(id);
		Member member = memberData.get();

		String name = jObject.isNull("memberName") ? member.getMemberName() : jObject.getString("memberName");
		Integer age = jObject.isNull("memberAge") ? member.getMemberAge() : jObject.getInt("memberAge");
		String gender = jObject.isNull("memberGender") ? member.getMemberGender() : jObject.getString("memberGender");
		String tel = jObject.isNull("memberTel") ? member.getMemberTel() : jObject.getString("memberTel");
		String address = jObject.isNull("memberAddress") ? member.getMemberAddress() : jObject.getString("memberAddress");

		System.out.println(member);
		
		return memberDao.updateMemberById(id, name, age, gender, tel, address);
		
		

	}
	
	public Integer updateImg(Integer id, byte[] memberImg) {
		
		Optional<Member> memberData = memberDao.findById(id);
		Member member = memberData.get();
		
		if(member!=null) {
			member.setMemberImg(memberImg);		
			return memberDao.updateImgById(id, memberImg);
		}
		
		return null;
	}

	public Member findMemberById(Integer id) {

		Optional<Member> option = memberDao.findById(id);

		if (option.isPresent()) {
			return option.get();
		}

		return null;
	}

	public Member findMemberByEmail(String memberEmail) {

		Member member = memberDao.findMemberByEmail(memberEmail);

		if (member != null) {
			return member;
		}
		return null;
	}

	public byte[] findMemberImg(Integer memberId) {

		Optional<Member> member = memberDao.findById(memberId);
		
		if(member != null) {
			return member.get().getMemberImg();
			
		}

			return null;
	}

	public byte[] findMemberImg(String memberEmail) {

		Optional<Member> member = Optional.ofNullable(memberDao.findMemberByEmail(memberEmail));
		
		if(member != null) {
			
			return member.get().getMemberImg();
		}

			return null;
	}

}
