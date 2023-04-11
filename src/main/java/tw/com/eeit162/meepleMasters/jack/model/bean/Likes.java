package tw.com.eeit162.meepleMasters.jack.model.bean;

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
@Table(name = "likes")
public class Likes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "likesId")
	private Integer likesId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "createdTime")
	private Date createdTime;
	
	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	@Column(name = "fk_postId")
	private Integer fkPostId;
	
	public Likes() {
	}

	public Likes(Date createdTime, Integer fkMemberId, Integer fkPostId) {
		super();
		this.createdTime = createdTime;
		this.fkMemberId = fkMemberId;
		this.fkPostId = fkPostId;
	}

	public Integer getLikesId() {
		return likesId;
	}

	public void setLikesId(Integer likesId) {
		this.likesId = likesId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getFkMemberId() {
		return fkMemberId;
	}

	public void setFkMemberId(Integer fkMemberId) {
		this.fkMemberId = fkMemberId;
	}

	public Integer getFkPostId() {
		return fkPostId;
	}

	public void setFkPostId(Integer fkPostId) {
		this.fkPostId = fkPostId;
	}

	@Override
	public String toString() {
		return "Likes [likesId=" + likesId + ", createdTime=" + createdTime + ", fkMemberId=" + fkMemberId
				+ ", fkPostId=" + fkPostId + "]";
	}
	
	

}
