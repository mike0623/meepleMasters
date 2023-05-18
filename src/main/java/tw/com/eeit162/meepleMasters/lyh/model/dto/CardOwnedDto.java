package tw.com.eeit162.meepleMasters.lyh.model.dto;

public class CardOwnedDto {
	
	private Integer ownedId;
	private Integer cardId;
	private Integer cardStatus;
	private String cardName;
	private Integer cardStar;
	
	public Integer getOwnedId() {
		return ownedId;
	}
	public void setOwnedId(Integer ownedId) {
		this.ownedId = ownedId;
	}
	public Integer getCardId() {
		return cardId;
	}
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
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
