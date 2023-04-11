package tw.com.eeit162.meepleMasters.lyh.model.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cardAuctionHistory")
public class CardAuctionHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cardAuctionHistoryId")
	private Integer cardAuctionHistoryId;
	
	@Column(name = "fk_auctionId")
	private Integer fkAuctionId;
	
	@Column(name = "fk_bidderId")
	private Integer fkBidderId;
	
	@Column(name = "bidPrice")
	private Integer bidPrice;
	
	@Column(name = "bidTime")
	private Date bidTime;
	
	public CardAuctionHistory() {
	}

	public Integer getCardAuctionHistoryId() {
		return cardAuctionHistoryId;
	}

	public void setCardAuctionHistoryId(Integer cardAuctionHistoryId) {
		this.cardAuctionHistoryId = cardAuctionHistoryId;
	}

	public Integer getFkAuctionId() {
		return fkAuctionId;
	}

	public void setFkAuctionId(Integer fkAuctionId) {
		this.fkAuctionId = fkAuctionId;
	}

	public Integer getFkBidderId() {
		return fkBidderId;
	}

	public void setFkBidderId(Integer fkBidderId) {
		this.fkBidderId = fkBidderId;
	}

	public Integer getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(Integer bidPrice) {
		this.bidPrice = bidPrice;
	}

	public Date getBidTime() {
		return bidTime;
	}

	public void setBidTime(Date bidTime) {
		this.bidTime = bidTime;
	}

	
}
