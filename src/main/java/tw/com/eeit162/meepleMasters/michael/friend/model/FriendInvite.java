package tw.com.eeit162.meepleMasters.michael.friend.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "friendInvite")
public class FriendInvite implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "friendInviteId")
	private Integer friendInviteId;
	
	@Column(name = "fk_inviterId")
	private Integer fkInviterId;
	
	@Column(name = "fk_accepterId")
	private Integer fkAccepterId;

	public FriendInvite() {
	}

	public Integer getFriendInviteId() {
		return friendInviteId;
	}

	public void setFriendInviteId(Integer friendInviteId) {
		this.friendInviteId = friendInviteId;
	}

	public Integer getFkInviterId() {
		return fkInviterId;
	}

	public void setFkInviterId(Integer fkInviterId) {
		this.fkInviterId = fkInviterId;
	}

	public Integer getFkAccepterId() {
		return fkAccepterId;
	}

	public void setFkAccepterId(Integer fkAccepterId) {
		this.fkAccepterId = fkAccepterId;
	}

	

	
}
