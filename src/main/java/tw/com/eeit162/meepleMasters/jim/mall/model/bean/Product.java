package tw.com.eeit162.meepleMasters.jim.mall.model.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

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

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "addedTime")
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

	@PrePersist
	public void onCreate() {
		if (addedTime == null) {
			addedTime = new Date();
		}
	}

	public Product() {
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

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productPrice=" + productPrice
				+ ", productImg=" + Arrays.toString(productImg) + ", addedTime=" + addedTime + ", productDescription="
				+ productDescription + ", productPlayTime=" + productPlayTime + ", productMaxPlayer=" + productMaxPlayer
				+ ", productMinPlayer=" + productMinPlayer + ", productDifficulty=" + productDifficulty + "]";
	}

}
