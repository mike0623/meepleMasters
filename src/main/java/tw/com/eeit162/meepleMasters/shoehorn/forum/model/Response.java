package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="response")
public class Response {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="responseId")
	private Integer responseId;
	
	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	@Column(name = "fk_articleId")
	private Integer fkArticleId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss EEE",timezone = "GMT+8")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "responseCreatedDate")
	private Date responseCreatedDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss EEE",timezone = "GMT+8")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "responseUpdatedDate")
	private Date responseUpdatedDate;
	
	@Column(name = "responseContent")
	private String responseContent;

	public Response() {
		super();
	}

	public Integer getResponseId() {
		return responseId;
	}

	public void setResponseId(Integer responseId) {
		this.responseId = responseId;
	}

	public Integer getFkMemberId() {
		return fkMemberId;
	}

	public void setFkMemberId(Integer fkMemberId) {
		this.fkMemberId = fkMemberId;
	}

	public Integer getFkArticleId() {
		return fkArticleId;
	}

	public void setFkArticleId(Integer fkArticleId) {
		this.fkArticleId = fkArticleId;
	}

	public Date getResponseCreatedDate() {
		return responseCreatedDate;
	}

	public void setResponseCreatedDate(Date responseCreatedDate) {
		this.responseCreatedDate = responseCreatedDate;
	}

	public Date getResponseUpdatedDate() {
		return responseUpdatedDate;
	}

	public void setResponseUpdatedDate(Date responseUpdatedDate) {
		this.responseUpdatedDate = responseUpdatedDate;
	}

	public String getResponseContent() {
		return responseContent;
	}

	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}

	
}
