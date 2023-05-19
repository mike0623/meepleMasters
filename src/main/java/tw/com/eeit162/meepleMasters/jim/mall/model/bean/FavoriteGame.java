package tw.com.eeit162.meepleMasters.jim.mall.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;

@Entity
@Table(name = "favoriteGame")
public class FavoriteGame implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "favoriteGameId")
	private Integer favoriteGameId;
	
	@JoinColumn(name = "fk_memberId", referencedColumnName = "memberId", nullable = false)
	@ManyToOne
	private Member member;

	@JoinColumn(name = "fk_productId", referencedColumnName = "productId", nullable = false)
	@ManyToOne
	private Product product;

	public FavoriteGame() {
	}

	public Integer getFavoriteGameId() {
		return favoriteGameId;
	}

	public void setFavoriteGameId(Integer favoriteGameId) {
		this.favoriteGameId = favoriteGameId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
