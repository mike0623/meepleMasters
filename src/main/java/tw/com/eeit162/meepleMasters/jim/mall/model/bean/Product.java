package tw.com.eeit162.meepleMasters.jim.mall.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "productId")
	private Integer productId;

	@Column(name = "productName")
	private String productName;

	@Column(name = "productPrice")
	private Integer productPrice;

	@JsonIgnore
	@Column(name = "productImg")
	private byte[] productImg;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "addedTime", columnDefinition = "DATE")
	private Date addedTime;

	@Column(name = "productDescription")
	private String productDescription;

	@Column(name = "productPlayTime")
	private String productPlayTime;

	@Column(name = "productMaxPlayer")
	private Integer productMaxPlayer;

	@Column(name = "productMinPlayer")
	private Integer productMinPlayer;

	@Column(name = "productDifficulty")
	private String productDifficulty;

	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ShoppingCart> shoppingCarts;

	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<FavoriteGame> favoriteGames;

	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderDetail> orderDetails;

	@PrePersist
	public void onCreate() {
		if (addedTime == null) {
			addedTime = new Date();
		}
	}

	public Product() {
	}

	public Product(Integer productId) {
		this.productId = productId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public byte[] getProductImg() {
		return productImg;
	}

	public void setProductImg(byte[] productImg) {
		this.productImg = productImg;
	}

	public Date getAddedTime() {
		return addedTime;
	}

	public void setAddedTime(Date addedTime) {
		this.addedTime = addedTime;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductPlayTime() {
		return productPlayTime;
	}

	public void setProductPlayTime(String productPlayTime) {
		this.productPlayTime = productPlayTime;
	}

	public Integer getProductMaxPlayer() {
		return productMaxPlayer;
	}

	public void setProductMaxPlayer(Integer productMaxPlayer) {
		this.productMaxPlayer = productMaxPlayer;
	}

	public Integer getProductMinPlayer() {
		return productMinPlayer;
	}

	public void setProductMinPlayer(Integer productMinPlayer) {
		this.productMinPlayer = productMinPlayer;
	}

	public String getProductDifficulty() {
		return productDifficulty;
	}

	public void setProductDifficulty(String productDifficulty) {
		this.productDifficulty = productDifficulty;
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

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId, productName, productPrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(productId, other.productId) && Objects.equals(productName, other.productName)
				&& Objects.equals(productPrice, other.productPrice);
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + "]";
	}

}
