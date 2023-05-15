package tw.com.eeit162.meepleMasters.lyh.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CardReleasedDto {

	private Integer releasedId;
	private Integer ownedId;
	private Integer directPrice;
	private Integer startPrice;
	private Integer type;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
	private Date startTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
	private Date endTime;
	private Integer releasedStatus;
	private Integer cardId;
	private Integer memberId;
	private Integer cardStatus;
	private String cardName;
	private Integer cardStar;
	private String memberName;
	private Integer purchasePrice;
	private String purchaserName;
	private Integer purchaserId;
	
	
	public Integer getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(Integer purchaserId) {
		this.purchaserId = purchaserId;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public Integer getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Integer purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getReleasedId() {
		return releasedId;
	}

	public void setReleasedId(Integer releasedId) {
		this.releasedId = releasedId;
	}

	public Integer getOwnedId() {
		return ownedId;
	}

	public void setOwnedId(Integer ownedId) {
		this.ownedId = ownedId;
	}

	public Integer getDirectPrice() {
		return directPrice;
	}

	public void setDirectPrice(Integer directPrice) {
		this.directPrice = directPrice;
	}

	public Integer getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Integer startPrice) {
		this.startPrice = startPrice;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getReleasedStatus() {
		return releasedStatus;
	}

	public void setReleasedStatus(Integer releasedStatus) {
		this.releasedStatus = releasedStatus;
	}

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(Integer cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public Integer getCardStar() {
		return cardStar;
	}

	public void setCardStar(Integer cardStar) {
		this.cardStar = cardStar;
	}

	@Override
	public String toString() {
		return "CardReleasedDto [releasedId=" + releasedId + ", ownedId=" + ownedId + ", directPrice=" + directPrice
				+ ", startPrice=" + startPrice + ", type=" + type + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", releasedStatus=" + releasedStatus + ", cardId=" + cardId + ", memberId=" + memberId
				+ ", cardStatus=" + cardStatus + ", cardName=" + cardName + ", cardStar=" + cardStar + ", memberName="
				+ memberName + "]";
	}

	
}
