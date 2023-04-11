package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

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
@Table(name = "responseComment")
public class ResponseComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="responseCommentId")
	private Integer responseCommentId;
	
	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	@Column(name = "fk_responseId")
	private Integer fkResponseId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss EEE",timezone = "GMT+8")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "responseCommentCreatedDate")
	private Date responseCommentCreatedDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss EEE",timezone = "GMT+8")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "responseCommentUpdatedDate")
	private Date responseCommentUpdatedDate;
	
	@Column(name = "responseCommentContent")
	private String responseCommentContent;

	public ResponseComment() {
		super();
	}

	public Integer getResponseCommentId() {
		return responseCommentId;
	}

	public void setResponseCommentId(Integer responseCommentId) {
		this.responseCommentId = responseCommentId;
	}

	public Integer getFkMemberId() {
		return fkMemberId;
	}

	public void setFkMemberId(Integer fkMemberId) {
		this.fkMemberId = fkMemberId;
	}

	public Integer getFkResponseId() {
		return fkResponseId;
	}

	public void setFkResponseId(Integer fkResponseId) {
		this.fkResponseId = fkResponseId;
	}

	public Date getResponseCommentCreatedDate() {
		return responseCommentCreatedDate;
	}

	public void setResponseCommentCreatedDate(Date responseCommentCreatedDate) {
		this.responseCommentCreatedDate = responseCommentCreatedDate;
	}

	public Date getResponseCommentUpdatedDate() {
		return responseCommentUpdatedDate;
	}

	public void setResponseCommentUpdatedDate(Date responseCommentUpdatedDate) {
		this.responseCommentUpdatedDate = responseCommentUpdatedDate;
	}

	public String getResponseCommentContent() {
		return responseCommentContent;
	}

	public void setResponseCommentContent(String responseCommentContent) {
		this.responseCommentContent = responseCommentContent;
	}

}
