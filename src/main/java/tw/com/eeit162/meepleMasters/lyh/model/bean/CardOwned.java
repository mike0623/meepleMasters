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
@Table(name = "cardOwned")
public class CardOwned {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ownedId")
	private Integer ownedId;
	
	@Column(name = "fk_cardId")
	private Integer fkCardId;
	
	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "ownedTime")
	private Date ownedTime;
	
	@Column(name = "cardStatus")
	private Integer cardStatus;
	
	public CardOwned() {
	}

	public Integer getOwnedId() {
		return ownedId;
	}

	public void setOwnedId(Integer ownedId) {
		this.ownedId = ownedId;
	}

	public Integer getFkCardId() {
		return fkCardId;
	}

	public void setFkCardId(Integer fkCardId) {
		this.fkCardId = fkCardId;
	}

	public Integer getFkMemberId() {
		return fkMemberId;
	}

	public void setFkMemberId(Integer fkMemberId) {
		this.fkMemberId = fkMemberId;
	}

	public Date getOwnedTime() {
		return ownedTime;
	}

	public void setOwnedTime(Date ownedTime) {
		this.ownedTime = ownedTime;
	}

	public Integer getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(Integer cardStatus) {
		this.cardStatus = cardStatus;
	}

	@Override
	public String toString() {
		return "CardOwned [ownedId=" + ownedId + ", fkCardId=" + fkCardId + ", fkMemberId=" + fkMemberId
				+ ", ownedTime=" + ownedTime + ", cardStatus=" + cardStatus + "]";
	}
	
	

}
