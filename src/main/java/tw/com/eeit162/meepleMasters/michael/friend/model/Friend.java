package tw.com.eeit162.meepleMasters.michael.friend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="friend")
public class Friend implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "friendId")
	private Integer friendId;
	
	@Column(name = "fk_memberAId")
	private Integer fkMemberAId;
	
	@Column(name = "fk_memberBId")
	private Integer fkMemberBId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "friendCreatedDate")
	private Date friendCreatedDate;

	public Friend() {
	}
	
	@PrePersist
	public void onCreate() {
		if(friendCreatedDate == null) {
			friendCreatedDate = new Date();
		}
	}
	

	public Integer getFriendId() {
		return friendId;
	}

	public void setFriendId(Integer friendId) {
		this.friendId = friendId;
	}

	public Integer getFkMemberAId() {
		return fkMemberAId;
	}

	public void setFkMemberAId(Integer fkMemberAId) {
		this.fkMemberAId = fkMemberAId;
	}

	public Integer getFkMemberBId() {
		return fkMemberBId;
	}

	public void setFkMemberBId(Integer fkMemberBId) {
		this.fkMemberBId = fkMemberBId;
	}

	public Date getFriendCreatedDate() {
		return friendCreatedDate;
	}

	public void setFriendCreatedDate(Date friendCreatedDate) {
		this.friendCreatedDate = friendCreatedDate;
	}
	
	

}
