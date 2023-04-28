package tw.com.eeit162.meepleMasters.michael.message.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MessageDto {
	
	private String sender;
	
	private String receiver;
	
	private String text;
	
	private Integer stickerId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Taipei")
	private Date createdDate;
	
	//以下為上線時需傳出的資料
	private Integer chatOrderWhenLeave;
	
	
	private Integer notRead;
	
	
	private String chatToWhom;
	
	
	private String chatToWhomName;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getStickerId() {
		return stickerId;
	}

	public void setStickerId(Integer stickerId) {
		this.stickerId = stickerId;
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

	public Integer getNotRead() {
		return notRead;
	}

	public void setNotRead(Integer notRead) {
		this.notRead = notRead;
	}

	public String getChatToWhom() {
		return chatToWhom;
	}

	public void setChatToWhom(String chatToWhom) {
		this.chatToWhom = chatToWhom;
	}

	public String getChatToWhomName() {
		return chatToWhomName;
	}

	public void setChatToWhomName(String chatToWhomName) {
		this.chatToWhomName = chatToWhomName;
	}
	
	
	
	
}
