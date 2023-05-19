package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "articleReview")
public class ArticleReview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="articleReviewId")
	private Integer articleReviewId;
	
	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	@Column(name = "fk_articleId")
	private Integer fkArticleId;
	
	@Column(name = "articleReview")
	private Boolean articleReview;
	

	public ArticleReview() {
		super();
	}

	public Integer getArticleReviewId() {
		return articleReviewId;
	}

	public void setArticleReviewId(Integer articleReviewId) {
		this.articleReviewId = articleReviewId;
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

	public Boolean getArticleReview() {
		return articleReview;
	}

	public void setArticleReview(Boolean articleReview) {
		this.articleReview = articleReview;
	}


	
	
}
