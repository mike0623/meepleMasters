package tw.com.eeit162.meepleMasters.michael.message.model;

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
@Table(name="chatRoomOrder")
public class ChatRoomOrder {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chatRoomOrderId")
	private Integer chatRoomOrderId;
	
	@Column(name = "fk_owner")
	private Integer fkOwner;
	
	@Column(name = "fk_chatToWhom")
	private Integer fkChatToWhom;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Taipei")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "createdDate")
	private Date createdDate;
	
	@Column(name = "chatOrderWhenLeave")
	private Integer chatOrderWhenLeave;
	
	
	
	@PrePersist
	public void onCreate() {
		if(createdDate == null) {
			createdDate = new Date();
		}
	}
	
	public ChatRoomOrder() {
	}

	public Integer getChatRoomOrderId() {
		return chatRoomOrderId;
	}

	public void setChatRoomOrderId(Integer chatRoomOrderId) {
		this.chatRoomOrderId = chatRoomOrderId;
	}

	public Integer getFkOwner() {
		return fkOwner;
	}

	public void setFkOwner(Integer fkOwner) {
		this.fkOwner = fkOwner;
	}

	public Integer getFkChatToWhom() {
		return fkChatToWhom;
	}

	public void setFkChatToWhom(Integer fkChatToWhom) {
		this.fkChatToWhom = fkChatToWhom;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getChatOrderWhenLeave() {
		return chatOrderWhenLeave;
	}

	public void setChatOrderWhenLeave(Integer chatOrderWhenLeave) {
		this.chatOrderWhenLeave = chatOrderWhenLeave;
	}
	
	

}
