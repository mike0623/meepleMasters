package tw.com.eeit162.meepleMasters.jim.mall.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shoppingCart")
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cartId")
	private Integer cartId;

	@Column(name = "fk_memberId")
	private Integer fkMemberId;

	@Column(name = "fk_productId")
	private Integer fkProductId;

	public ShoppingCart() {
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getFkMemberId() {
		return fkMemberId;
	}

	public void setFkMemberId(Integer fkMemberId) {
		this.fkMemberId = fkMemberId;
	}

	public Integer getFkProductId() {
		return fkProductId;
	}

	public void setFkProductId(Integer fkProductId) {
		this.fkProductId = fkProductId;
	}

}
