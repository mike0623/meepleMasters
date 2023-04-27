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
	
	@Column(name = "startPrice")
	private Integer startPrice;
	
	@Column(name = "directPrice")
	private Integer directPrice;
	
	@Column(name = "auctionStatus")
	private Integer auctionStatus;
	
	@Column(name = "fk_purchaserId")
	private Integer fkPurchaserId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

	public Integer getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Integer startPrice) {
		this.startPrice = startPrice;
	}

	public Integer getDirectPrice() {
		return directPrice;
	}

	public void setDirectPrice(Integer directPrice) {
		this.directPrice = directPrice;
	}

	public Integer getAuctionStatus() {
		return auctionStatus;
	}

	public void setAuctionStatus(Integer auctionStatus) {
		this.auctionStatus = auctionStatus;
	}

	public Integer getFkPurchaserId() {
		return fkPurchaserId;
	}

	public void setFkPurchaserId(Integer fkPurchaserId) {
		this.fkPurchaserId = fkPurchaserId;
	}

	public Date getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(Date purchaseTime) {
		this.purchaseTime = purchaseTime;
	}
	

}
