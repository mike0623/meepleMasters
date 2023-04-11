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
@Table(name = "blogResponse")
public class BlogResponse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "blogResponseId")
	private Integer blogResponseId;
	
	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	@Column(name = "fk_postId")
	private Integer fkPostId;
	
	@Column(name = "content")
	private String content;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "commentCreatedTime")
	private Date commentCreatedTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "commentUpdatedTime")
	private Date commentUpdatedTime;
	
	
	public BlogResponse() {
	}


	public Integer getBlogResponseId() {
		return blogResponseId;
	}


	public void setBlogResponseId(Integer blogResponseId) {
		this.blogResponseId = blogResponseId;
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


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Date getCommentCreatedTime() {
		return commentCreatedTime;
	}


	public void setCommentCreatedTime(Date commentCreatedTime) {
		this.commentCreatedTime = commentCreatedTime;
	}


	public Date getCommentUpdatedTime() {
		return commentUpdatedTime;
	}


	public void setCommentUpdatedTime(Date commentUpdatedTime) {
		this.commentUpdatedTime = commentUpdatedTime;
	}

	@Override
	public String toString() {
		return "BlogResponse [blogResponseId=" + blogResponseId + ", fkMemberId=" + fkMemberId + ", fkPostId="
				+ fkPostId + ", content=" + content + ", commentCreatedTime=" + commentCreatedTime
				+ ", commentUpdatedTime=" + commentUpdatedTime + "]";
	}
	
	
}
