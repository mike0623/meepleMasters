package tw.com.eeit162.meepleMasters.jack.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.favre.lib.crypto.bcrypt.BCrypt;
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

	/**
	 * 註冊會員
	 * 
	 * @param json
	 * @return Member
	 * @throws IOException
	 * @throws Exception
	 * @throws ParseException
	 */
	public Member createMember(String json) throws IOException, Exception, ParseException {
		JSONObject jObject = new JSONObject(json);

		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jObject.getString("memberBirth"));

		String email = jObject.getString("memberEmail");
		String password = jObject.getString("memberPwd");
		String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
		
		String name = jObject.getString("memberName");
		Date birth = jObject.isNull("memberBirth") ? null : date;
		String gender = jObject.isNull("memberGender") ? null : jObject.getString("memberGender");
		String tel = jObject.isNull("memberTel") ? null : jObject.getString("memberTel");
		String address = jObject.isNull("memberAddress") ? null : jObject.getString("memberAddress");
		

		Member member = new Member();
		member.setMemberEmail(email);
		member.setMemberPwd(bcryptHashString);
		member.setMemberName(name);
		member.setMemberLevel("一般會員");
		member.setMemberBirth(birth);
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

	/**
	 * google登入 註冊會員
	 * 
	 * @param payloadEmail
	 * @param payloadName
	 * @param payloadPicture
	 * @return
	 * @throws IOException
	 */
	public Member createByGoogle(String payloadEmail, String payloadName, String payloadPicture) throws IOException {

		Member exsistMember = memberDao.findMemberByEmail(payloadEmail);

		if (exsistMember == null) {
			URL url = new URL(payloadPicture);
			InputStream is = url.openStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] data = new byte[1024];

			int count;
			while ((count = bis.read(data, 0, 1024)) != -1) {
				output.write(data, 0, count);
			}

			byte[] imageData = output.toByteArray();
			System.out.println(imageData);
			output.close();
			bis.close();
			is.close();

			Member member = new Member();

			member.setMemberEmail(payloadEmail);
			member.setMemberName(payloadName);
			member.setMemberImg(imageData);
			member.setMemberLevel("一般會員");
			member.setMemberActive(1);
			member.setMemberCoin(500);
			member.setCreateTime(new Date());

			return memberDao.save(member);
		}

		return exsistMember;
	}

	/**
	 * 寄認證信
	 * 
	 * @param member
	 */
	public void sendVerificationEmail(Member member) {
		System.out.println("已寄出");
		String token = jwtService.generateToken(member);
		String subject = "MeepleMasters會員信箱驗證";
		String url = "http://localhost:8080/meeple-masters/auth/" + token;
		String content = "您好! 歡迎您加入Meeple Masters，<br/>" + "這封信是由Meeple Masters的會員註冊系統所寄出，<br/>"
				+ "<h4>請點選下面的連結來進行註冊的下一個步驟：<br/></h4>" + "<h3><a href=\"" + url + "\">" + "驗證連結" + "</a></h3>" + "<br/>"
				+ "※ 如果您無法連結信中網址，請讓我們知道。<br/>" + "<br/>" + "Meeple Masters團隊 敬上<br/>";
		mailService.sendEmail(member.getMemberEmail(), subject, content);
	}
	

	/**
	 * 會員認證
	 * 
	 * @param token
	 */
	public void activeAccount(String token) {
		if (jwtService.validateToken(token)) {
			String email = jwtService.extractEmail(token);
			Member member = findMemberByEmail(email);
			if (member != null) {
				member.setMemberActive(1);
				memberDao.save(member);
			}
		}
	}
	
	/**
	 * 忘記密碼
	 * 
	 * @param member
	 */
	public void sendUpdatePwdEmail(Member member) {
		System.out.println("已寄出");
		String token = jwtService.generateToken(member);
		String subject = "MeepleMasters會員密碼更改";
		String url = "http://localhost:8080/meeple-masters/updatePwd/" + token;
		String content = "您好! 這封信是由Meeple Masters的會員註冊系統所寄出，<br/>"
				+ "<h4>請點選下面的連結來進行密碼修改：<br/></h4>" + "<h3><a href=\"" + url + "\">" + "更改密碼連結" + "</a></h3>" + "<br/>"
				+ "※ 如果您無法連結信中網址，請讓我們知道。<br/>" + "<br/>" + "Meeple Masters團隊 敬上<br/>";
		mailService.sendEmail(member.getMemberEmail(), subject, content);
	}
	
	/**
	 * 更改密碼連結，導到更改密碼頁面
	 * 
	 * @param token
	 */
	public Member tokenfindemail(String token) {
		if (jwtService.validateToken(token)) {
			String email = jwtService.extractEmail(token);
			Member member = findMemberByEmail(email);
			if (member != null) {
				
				return member;
			}
		}
		return null;
	}

	/**
	 * 登入
	 * 
	 * @param json
	 * @return Optional
	 */
	public Optional<Member> login(String json) {
		JSONObject member = new JSONObject(json);
		String email = member.getString("memberEmail");
		String password = member.getString("memberPwd");
		
		
		Optional<Member> option = Optional.ofNullable(memberDao.findMemberByEmail(email));
		if (option.isEmpty()) {
			return Optional.empty();
		} else {
			String memberPwd = option.get().getMemberPwd();
			BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), memberPwd);
			if (result.verified) {
				return option;
			}
			return Optional.empty();
		}

	}

	/**
	 * 更新密碼
	 * 
	 * @param id
	 * @param json
	 * @return Integer
	 */
	public Member updatePwdByEmail(String email, String password) {
		Member member = memberDao.findMemberByEmail(email);
		if(member !=null) {
			String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
			member.setMemberPwd(bcryptHashString);
			
			return memberDao.save(member);
		}
		
		return null;

	}

	/**
	 * 更新會員資料
	 * 
	 * @param id
	 * @param json
	 * @return Integer
	 * @throws JSONException
	 * @throws ParseException
	 */
	public Integer updateMember(Integer id, String json) throws JSONException, ParseException {
		JSONObject jObject = new JSONObject(json);

		Optional<Member> memberData = memberDao.findById(id);
		Member member = memberData.get();

		System.out.println(jObject);

		String name = jObject.isNull("memberName") ? member.getMemberName() : jObject.getString("memberName");
		Date birth;
		if (jObject.isNull("memberBirth")) {
			birth = member.getMemberBirth();
		} else {
			birth = new SimpleDateFormat("yyyy-MM-dd").parse(jObject.getString("memberBirth"));
		}
		String gender = jObject.isNull("memberGender") ? member.getMemberGender() : jObject.getString("memberGender");
		String tel = jObject.isNull("memberTel") ? member.getMemberTel() : jObject.getString("memberTel");
		String address = jObject.isNull("memberAddress") ? member.getMemberAddress()
				: jObject.getString("memberAddress");

		System.out.println(member);

		return memberDao.updateMemberById(id, name, birth, gender, tel, address);

	}

	/**
	 * 更新會員圖片
	 * 
	 * @param id
	 * @param memberImg
	 * @return Integer
	 */
	public Integer updateImg(Integer id, byte[] memberImg) {

		Optional<Member> memberData = memberDao.findById(id);
		Member member = memberData.get();

		if (member != null) {
			member.setMemberImg(memberImg);
			return memberDao.updateImgById(id, memberImg);
		}

		return null;
	}

	/**
	 * id找會員
	 * 
	 * @param id
	 * @return Member
	 */
	public Member findMemberById(Integer id) {

		Optional<Member> option = memberDao.findById(id);

		if (option.isPresent()) {
			return option.get();
		}

		return null;
	}

	/**
	 * email找會員
	 * 
	 * @param memberEmail
	 * @return Member
	 */
	public Member findMemberByEmail(String memberEmail) {

		Member member = memberDao.findMemberByEmail(memberEmail);

		if (member != null) {
			return member;
		}
		return null;
	}

	/**
	 * id找會員圖片
	 * 
	 * @param memberId
	 * @return byte[]
	 */
	public byte[] findMemberImg(Integer memberId) {

		Optional<Member> member = memberDao.findById(memberId);

		if (member != null) {
			return member.get().getMemberImg();

		}

		return null;
	}

	/**
	 * email找會員圖片
	 * 
	 * @param memberEmail
	 * @return byte[]
	 */
	public byte[] findMemberImg(String memberEmail) {

		Optional<Member> member = Optional.ofNullable(memberDao.findMemberByEmail(memberEmail));

		if (member != null) {

			return member.get().getMemberImg();
		}

		return null;
	}

	public Optional<List<Member>> findmemberByName(String memberName) {

		Optional<List<Member>> member = Optional.ofNullable(memberDao.findMemberByName(memberName));

		if (member != null) {

			return member;
		}

		return null;
	}
	
	public Optional<List<Member>> findAllMember(){
		
		Optional<List<Member>> allMember = Optional.ofNullable(memberDao.findAll());
		
		if(allMember!= null) {
			return allMember;
		}
		
		return null;
		
	}
	
	public Member banMemberById(Integer memberId){
		Optional<Member> option = memberDao.findById(memberId);
		Member member = option.get();
		if(member != null && member.getMemberActive()==1) {
			member.setMemberActive(0);
			memberDao.save(member);
			return member;
		}
		if(member != null && member.getMemberActive()==0) {
			member.setMemberActive(1);
			memberDao.save(member);
			return member;
		}
		return null;
	}

	@Transactional
	public Integer updateMemberCoin(Integer memberId, Integer coin) {

		return memberDao.updateMemberCoin(memberId, coin);
	}
}
