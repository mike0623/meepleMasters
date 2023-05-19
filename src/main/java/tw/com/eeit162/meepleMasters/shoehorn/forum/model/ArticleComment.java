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
@Table(name = "articleComment")
public class ArticleComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="articleCommentId")
	private Integer articleCommentId;
	
	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	@Column(name = "fk_articleId")
	private Integer fkArticleId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss EEE",timezone = "GMT+8")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "articleCommentCreatedDate")
	private Date articleCommentCreatedDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss EEE",timezone = "GMT+8")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "articleCommentUpdatedDate")
	private Date articleCommentUpdatedDate;
	
	@Column(name = "articleCommentContent")
	private String articleCommentContent;

	public ArticleComment() {
		super();
	}

	public Integer getArticleCommentId() {
		return articleCommentId;
	}

	public void setArticleCommentId(Integer articleCommentId) {
		this.articleCommentId = articleCommentId;
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

	public Date getArticleCommentCreatedDate() {
		return articleCommentCreatedDate;
	}

	public void setArticleCommentCreatedDate(Date articleCommentCreatedDate) {
		this.articleCommentCreatedDate = articleCommentCreatedDate;
	}

	public Date getArticleCommentUpdatedDate() {
		return articleCommentUpdatedDate;
	}

	public void setArticleCommentUpdatedDate(Date articleCommentUpdatedDate) {
		this.articleCommentUpdatedDate = articleCommentUpdatedDate;
	}

	public String getArticleCommentContent() {
		return articleCommentContent;
	}

	public void setArticleCommentContent(String articleCommentContent) {
		this.articleCommentContent = articleCommentContent;
	}


	
	
}
