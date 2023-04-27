package tw.com.eeit162.meepleMasters.michael.message.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="messageText")
public class MessageText {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "messageTextId")
	private Integer messageTextId;
	
	@Column(name = "msgText")
	private String msgText;
	

	public MessageText() {
	}


	public Integer getMessageTextId() {
		return messageTextId;
	}


	public void setMessageTextId(Integer messageTextId) {
		this.messageTextId = messageTextId;
	}


	public String getMsgText() {
		return msgText;
	}


	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}



	
	

}
