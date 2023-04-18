package tw.com.eeit162.meepleMasters.jack.model.bean;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "member")
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "memberId")
	private Integer memberId;
	
	@Column(name = "memberEmail")
	private String memberEmail;
	
	@Column(name = "memberPwd")
	private String memberPwd;
	
	@Column(name = "memberLevel")
	private String memberLevel;
	
	@Column(name = "memberActive")
	private Integer active;
	
	@Column(name = "memberName")
	private String memberName;
	
	@Column(name = "memberImg")
	private byte[] memberImg;
	
	@Column(name = "memberAge")
	private Integer memberAge;
	
	@Column(name = "memberGender")
	private String memberGender;
	
	@Column(name = "memberTel")
	private Integer memberTel;
	
	@Column(name = "memberAddress")
	private String memberAddress;
	
	@Column(name = "memberCoin")
	private Integer memberCoin;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "createTime")
	private Date createTime;
	
	public Member() {
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberPwd() {
		return memberPwd;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public byte[] getMemberImg() {
		return memberImg;
	}

	public void setMemberImg(byte[] memberImg) {
		this.memberImg = memberImg;
	}

	public Integer getMemberAge() {
		return memberAge;
	}

	public void setMemberAge(Integer memberAge) {
		this.memberAge = memberAge;
	}

	public String getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}

	public Integer getMemberTel() {
		return memberTel;
	}

	public void setMemberTel(Integer memberTel) {
		this.memberTel = memberTel;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public Integer getMemberCoin() {
		return memberCoin;
	}

	public void setMemberCoin(Integer memberCoin) {
		this.memberCoin = memberCoin;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", memberEmail=" + memberEmail + ", memberPwd=" + memberPwd
				+ ", memberLevel=" + memberLevel + ", active=" + active + ", memberName=" + memberName + ", memberImg="
				+ Arrays.toString(memberImg) + ", memberAge=" + memberAge + ", memberGender=" + memberGender
				+ ", memberTel=" + memberTel + ", memberAddress=" + memberAddress + ", memberCoin=" + memberCoin
				+ ", createTime=" + createTime + "]";
	}
	
	
	
}
