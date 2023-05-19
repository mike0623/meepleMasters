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
@Table(name = "article")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "articleId")
	private Integer articleId;

	@Column(name = "fk_memberId")
	private Integer fkMemberId;

	@Column(name = "fk_productId")
	private Integer fkProductId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss EEE", timezone = "GMT+8")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "articleCreatedDate")
	private Date articleCreatedDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss EEE", timezone = "GMT+8")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "articleUpdatedDate")
	private Date articleUpdatedDate;

	@Column(name = "articleTitle")
	private String articleTitle;

//	@Lob
	@Column(name = "articleContent")
	private String articleContent;

	public Article() {
		super();
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getFkMemberId() {
		return fkMemberId;
	}

	public void setFkMemberId(Integer fkMemberId) {
		this.fkMemberId = fkMemberId;
	}

	public Integer getFkProductId() {
		return fkProductId;
	}

	public void setFkProductId(Integer fkProductId) {
		this.fkProductId = fkProductId;
	}

	public Date getArticleCreatedDate() {
		return articleCreatedDate;
	}

	public void setArticleCreatedDate(Date articleCreatedDate) {
		this.articleCreatedDate = articleCreatedDate;
	}

	public Date getArticleUpdatedDate() {
		return articleUpdatedDate;
	}

	public void setArticleUpdatedDate(Date articleUpdatedDate) {
		this.articleUpdatedDate = articleUpdatedDate;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

}
