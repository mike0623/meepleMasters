package tw.com.eeit162.meepleMasters.lyh.model.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cardDirect")
public class CardDirect {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "directId")
	private Integer directId;
	
	@Column(name = "fk_releasedId")
	private Integer fkReleasedId;
	
	@Column(name = "directPrice")
	private Integer directPrice;
	
	@Column(name = "fk_purchaserId")
	private Integer fkPurchaserId;
	
	@Column(name = "purchaseTime")
	private Date purchaseTime;
	
	@Column(name = "directStatus")
	private Integer directStatus;
	
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

	public Integer getDirectPrice() {
		return directPrice;
	}

	public void setDirectPrice(Integer directPrice) {
		this.directPrice = directPrice;
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

	public Integer getDirectStatus() {
		return directStatus;
	}

	public void setDirectStatus(Integer directStatus) {
		this.directStatus = directStatus;
	}
	
	

}
