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
//	@Autowired
//	private JavaMail javaMail;
	
	public void testMember(Member member) {
		Member member1 = new Member();
		member1.setMemberEmail("123@gmail.com");
		member1.setMemberPwd("1234");
		
		memberDao.save(member1);
		
	}
	
	public void verify() {
		JavaMail javaMail = new JavaMail();
		javaMail.sendMail();
	}
	
	public Member createMember(String json) throws IOException {
		 JSONObject jObject = new JSONObject(json);
		 
		 String email = jObject.getString("memberEmail");
		 String password = jObject.getString("memberPwd");
		 String name = jObject.getString("memberName");
		 Integer age = jObject.isNull("memberAge")?null:jObject.getInt("memberAge");
		 String gender = jObject.isNull("memberGender")?null:jObject.getString("memberGender");
		 Integer tel = jObject.isNull("memberTel")?null:jObject.getInt("memberTel");
		 String address	= jObject.isNull("memberAddress")?null:jObject.getString("memberAddress");
		 
		 Member member = new Member();
		 member.setMemberEmail(email);
		 member.setMemberPwd(password);
		 member.setMemberName(name);
		 member.setMemberLevel("一般會員");
		 member.setMemberAge(age);
		 member.setMemberGender(gender);
		 member.setMemberTel(tel);
		 member.setMemberAddress(address);
		 member.setMemberActive(0);
		 member.setCreateTime(new Date());
		 
		 File file = new File("");
		 String filePath = file.getAbsolutePath();
		 FileInputStream fis = new FileInputStream(new File(filePath+"\\src\\main\\webapp\\img\\user.png"));
		 BufferedInputStream bis = new BufferedInputStream(fis);
		 
		 member.setMemberImg(bis.readAllBytes());
		 
		 bis.close();
		 fis.close();
		 
		 memberDao.save(member);
		 
		
		return member;
	}
	
//	public String memberAuth() {
//		javaMail.sendMail();
//		
//		return "success";
//	}
	
	public Optional<Member> login(Member member) {
		
//		Member memberLogin = memberDao.findMemberByEmailandPassword(member.getMemberEmail(), member.getMemberPwd());
		return Optional.ofNullable(memberDao.findMemberByEmailandPassword(member.getMemberEmail(), member.getMemberPwd())); 
	}
	
	public Integer updatePwd(Integer id,String json) {
		JSONObject jObject = new JSONObject(json);
		
//		Integer id = jObject.isNull("memberId")?null:jObject.getInt("memberId");
		String password = jObject.isNull("memberPwd")?null:jObject.getString("memberPwd");
		
			return memberDao.updatePasswordById(id,password);

	}
	
	public Integer updateMember(Integer id, String json) {
		JSONObject jObject = new JSONObject(json);
		
		Optional<Member> memberData = memberDao.findById(id);
		Member member = memberData.get();
		
		String name = jObject.isNull("memberName")?member.getMemberName():jObject.getString("memberName");
		Integer age = jObject.isNull("memberAge")?member.getMemberAge():jObject.getInt("memberAge");
		String gender = jObject.isNull("memberGender")?member.getMemberGender():jObject.getString("memberGender");
		Integer tel = jObject.isNull("memberTel")?member.getMemberTel():jObject.getInt("memberTel");
		String address	= jObject.isNull("memberAddress")?member.getMemberAddress():jObject.getString("memberAddress");
		
		System.out.println(member);
		
		return memberDao.updateMemberById(id, name, age, gender, tel, address);
		
	}
	
	public Member findMemberById(Integer id) {
		
		Optional<Member> option = memberDao.findById(id);
		
		if(option.isPresent()) {
			return option.get();
		}
		
		return null;
	}
	
	public Member findMemberByEmail(String memberEmail) {
		
		Member member = memberDao.findMemberByEmail(memberEmail);
		
		if(member!=null) {
			return member;
		}
		return null;
	}
	
	public byte[] findMemberImg(Integer memberId) {
		
		Optional<Member> member = memberDao.findById(memberId);
		
		return member.get().getMemberImg();
		
	}
	
}
