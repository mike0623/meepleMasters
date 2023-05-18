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
@Table(name = "cardDirect")
public class CardDirect {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "directId")
	private Integer directId;
	
	@Column(name = "fk_releasedId")
	private Integer fkReleasedId;
	
	@Column(name = "fk_purchaserId")
	private Integer fkPurchaserId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "purchaseTime")
	private Date purchaseTime;
	
	public CardDirect() {
	}

	public Integer getDirectId() {
		return directId;
	}

	public void setDirectId(Integer directId) {
		this.directId = directId;
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

	public Date getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(Date purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	@Override
	public String toString() {
		return "CardDirect [directId=" + directId + ", fkReleasedId=" + fkReleasedId + ", fkPurchaserId="
				+ fkPurchaserId + ", purchaseTime=" + purchaseTime + "]";
	}
	
}
