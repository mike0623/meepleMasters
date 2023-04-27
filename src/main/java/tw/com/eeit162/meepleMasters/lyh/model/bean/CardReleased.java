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
	
	@Column(name = "type")
	private Integer type;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "startTime")
	private Date startTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "endTime")
	private Date endTime;

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

}
