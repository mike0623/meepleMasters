package tw.com.eeit162.meepleMasters.lyh.model.dto;

import java.util.Date;

public class CardDto {

	private Integer releasedId;
	private Integer ownedId;
	private Integer directPrice;
	private Integer startPrice;
	private Integer type;
	private Date endTime;
	private Integer releasedStatus;
	private Integer cardId;
	private Integer memberId;
	private Integer cardStatus;
	private String cardName;
	private Integer cardStar;

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

}
