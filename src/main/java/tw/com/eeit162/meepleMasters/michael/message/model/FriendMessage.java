package tw.com.eeit162.meepleMasters.michael.message.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "friendMessage")
public class FriendMessage implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "friendMessageId")
	private Integer friendMessageId;
	
	@Column(name = "fk_senderId")
	private Integer fkSenderId;
	
	@Column(name = "fk_receiverId")
	private Integer fkReceiverId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "friendMessageCreatedDate")
	private Date friendMessageCreatedDate;
	
	@Column(name = "messageContext")
	private String messageContext;
	
	@Lob
	@Column(name = "messageImg")
	private byte[] messageImg;
	
	public FriendMessage() {
	}
	
	@PrePersist
	public void onCreate() {
		if(friendMessageCreatedDate == null) {
			friendMessageCreatedDate = new Date();
		}
	}
	

	public Integer getFriendMessageId() {
		return friendMessageId;
	}

	public void setFriendMessageId(Integer friendMessageId) {
		this.friendMessageId = friendMessageId;
	}

	public Integer getFkSenderId() {
		return fkSenderId;
	}

	public void setFkSenderId(Integer fkSenderId) {
		this.fkSenderId = fkSenderId;
	}

	public Integer getFkReceiverId() {
		return fkReceiverId;
	}

	public void setFkReceiverId(Integer fkReceiverId) {
		this.fkReceiverId = fkReceiverId;
	}

	public Date getFriendMessageCreatedDate() {
		return friendMessageCreatedDate;
	}

	public void setFriendMessageCreatedDate(Date friendMessageCreatedDate) {
		this.friendMessageCreatedDate = friendMessageCreatedDate;
	}

	public String getMessageContext() {
		return messageContext;
	}

	public void setMessageContext(String messageContext) {
		this.messageContext = messageContext;
	}

	public byte[] getMessageImg() {
		return messageImg;
	}

	public void setMessageImg(byte[] messageImg) {
		this.messageImg = messageImg;
	}
	
	

}
