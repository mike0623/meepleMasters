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
@Table(name = "cardReleased")
public class CardReleased {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "releasedId")
	private Integer releasedId;
	
	@Column(name = "fk_ownedId")
	private Integer fkOwnedId;
	
	@Column(name = "directPrice")
	private Integer directPrice;
	
	@Column(name = "startPrice")
	private Integer startPrice;
	
	@Column(name = "type")
	private Integer type;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "startTime")
	private Date startTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "endTime")
	private Date endTime;
	
	@Column(name = "releasedStatus")
	private Integer releasedStatus;

	public CardReleased() {
	}

	public Integer getReleasedId() {
		return releasedId;
	}

	public void setReleasedId(Integer releasedId) {
		this.releasedId = releasedId;
	}

	public Integer getFkOwnedId() {
		return fkOwnedId;
	}

	public void setFkOwnedId(Integer fkOwnedId) {
		this.fkOwnedId = fkOwnedId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public Integer getReleasedStatus() {
		return releasedStatus;
	}

	public void setReleasedStatus(Integer releasedStatus) {
		this.releasedStatus = releasedStatus;
	}

	@Override
	public String toString() {
		return "CardReleased [releasedId=" + releasedId + ", fkOwnedId=" + fkOwnedId + ", directPrice=" + directPrice
				+ ", startPrice=" + startPrice + ", type=" + type + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", releasedStatus=" + releasedStatus + ", getReleasedId()=" + getReleasedId() + ", getFkOwnedId()="
				+ getFkOwnedId() + ", getType()=" + getType() + ", getStartTime()=" + getStartTime() + ", getEndTime()="
				+ getEndTime() + ", getDirectPrice()=" + getDirectPrice() + ", getStartPrice()=" + getStartPrice()
				+ ", getReleasedStatus()=" + getReleasedStatus() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
