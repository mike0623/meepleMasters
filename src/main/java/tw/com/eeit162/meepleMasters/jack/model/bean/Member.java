package tw.com.eeit162.meepleMasters.jack.model.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.FavoriteGame;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Order;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.ShoppingCart;

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
	private Integer memberActive;

	@Column(name = "memberName")
	private String memberName;

	@Column(name = "memberImg")
	private byte[] memberImg;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "memberBirth")
	private Date memberBirth;

	@Column(name = "memberGender")
	private String memberGender;

	@Column(name = "memberTel")
	private String memberTel;

	@Column(name = "memberAddress")
	private String memberAddress;

	@Column(name = "memberCoin")
	private Integer memberCoin;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "createTime")
	private Date createTime;

	@JsonIgnore
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<ShoppingCart> shoppingCarts;

	@JsonIgnore
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<FavoriteGame> favoriteGames;

	@JsonIgnore
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Order> orders;

	public Member() {
	}

	public Member(Integer memberId) {
		this.memberId = memberId;
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

	public Integer getMemberActive() {
		return memberActive;
	}

	public void setMemberActive(Integer memberActive) {
		this.memberActive = memberActive;
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

	public Date getMemberBirth() {
		return memberBirth;
	}

	public void setMemberBirth(Date memberBirth) {
		this.memberBirth = memberBirth;
	}

	public String getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}

	public String getMemberTel() {
		return memberTel;
	}

	public void setMemberTel(String memberTel) {
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

	public List<ShoppingCart> getShoppingCarts() {
		return shoppingCarts;
	}

	public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
		this.shoppingCarts = shoppingCarts;
	}

	public List<FavoriteGame> getFavoriteGames() {
		return favoriteGames;
	}

	public void setFavoriteGames(List<FavoriteGame> favoriteGames) {
		this.favoriteGames = favoriteGames;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", memberName=" + memberName + "]";
	}

}
