package tw.com.eeit162.meepleMasters.lyh.model.bean;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "card")
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cardId")
	private Integer cardId;
	
	@Column(name = "cardName")
	private String cardName;

	@Column(name = "cardStar")
	private Integer cardStar;
	
	@Column(name = "cardImg")
	private byte[] cardImg;
	
	public Card() {
	}

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
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

	public byte[] getCardImg() {
		return cardImg;
	}

	public void setCardImg(byte[] cardImg) {
		this.cardImg = cardImg;
	}

	@Override
	public String toString() {
		return "Card [cardId=" + cardId + ", cardName=" + cardName + ", cardStar=" + cardStar + ", cardImg="
				+ Arrays.toString(cardImg) + "]";
	}
	
	

}
