package tw.com.eeit162.meepleMasters.jim.mall.model.dto;

import java.util.Date;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;

public class ProductDTO {
	private Integer productId;

	private String productName;

	private Integer productPrice;

	private byte[] productImg;

	private Date addedTime;

	private String productDescription;

	private String productPlayTime;

	private Integer productMaxPlayer;

	private Integer productMinPlayer;

	private String productDifficulty;

	private Boolean isInCart;

	private Boolean isFavorite;

	private Boolean isInLibrary;

	public ProductDTO(Product p) {
		this.productId = p.getProductId();
		this.productName = p.getProductName();
		this.productPrice = p.getProductPrice();
		this.addedTime = p.getAddedTime();
		this.productDescription = p.getProductDescription();
		this.productPlayTime = p.getProductPlayTime();
		this.productMaxPlayer = p.getProductMaxPlayer();
		this.productMinPlayer = p.getProductMinPlayer();
		this.productDifficulty = p.getProductDifficulty();
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

	public Boolean getIsInCart() {
		return isInCart;
	}

	public void setIsInCart(Boolean isInCart) {
		this.isInCart = isInCart;
	}

	public Boolean getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	public Boolean getIsInLibrary() {
		return isInLibrary;
	}

	public void setIsInLibrary(Boolean isInLibrary) {
		this.isInLibrary = isInLibrary;
	}

}
