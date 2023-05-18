package tw.com.eeit162.meepleMasters.lyh.model.bean;

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
@Table(name = "cardAuction")
public class CardAuction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auctionId")
	private Integer auctionId;
	
	@Column(name = "fk_releasedId")
	private Integer fkReleasedId;
	
	@Column(name = "fk_purchaserId")
	private Integer fkPurchaserId;
	
	@Column(name = "purchasePrice")
	private Integer purchasePrice;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "purchaseTime")
	private Date purchaseTime;
	
	public CardAuction() {
	}

	public Integer getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(Integer auctionId) {
		this.auctionId = auctionId;
	}

	public Integer getFkReleasedId() {
		return fkReleasedId;
	}

	public void setFkReleasedId(Integer fkReleasedId) {
		this.fkReleasedId = fkReleasedId;
	}

	public Integer getFkPurchaserId() {
		return fkPurchaserId;
	}

	public void setFkPurchaserId(Integer fkPurchaserId) {
		this.fkPurchaserId = fkPurchaserId;
	}

	public Integer getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Integer purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Date getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(Date purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	@Override
	public String toString() {
		return "CardAuction [auctionId=" + auctionId + ", fkReleasedId=" + fkReleasedId + ", fkPurchaserId="
				+ fkPurchaserId + ", purchasePrice=" + purchasePrice + ", purchaseTime=" + purchaseTime + "]";
	}
	
}
