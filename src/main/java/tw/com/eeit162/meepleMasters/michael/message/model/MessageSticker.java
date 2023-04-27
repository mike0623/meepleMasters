package tw.com.eeit162.meepleMasters.michael.message.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="messageSticker")
public class MessageSticker {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "messageStickerId")
	private Integer messageStickerId;
	
	@Column(name = "fk_stickerId")
	private Integer fkStickerId;
	
	

	public MessageSticker() {
	}



	public Integer getMessageStickerId() {
		return messageStickerId;
	}



	public void setMessageStickerId(Integer messageStickerId) {
		this.messageStickerId = messageStickerId;
	}



	public Integer getFkStickerId() {
		return fkStickerId;
	}



	public void setFkStickerId(Integer fkStickerId) {
		this.fkStickerId = fkStickerId;
	}
	
	

}
