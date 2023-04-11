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
	
	@Column(name = "articleGoodReview")
	private String articleGoodReview;
	
	@Column(name = "articleBadReview")
	private String articleBadReview;

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

	public String getArticleGoodReview() {
		return articleGoodReview;
	}

	public void setArticleGoodReview(String articleGoodReview) {
		this.articleGoodReview = articleGoodReview;
	}

	public String getArticleBadReview() {
		return articleBadReview;
	}

	public void setArticleBadReview(String articleBadReview) {
		this.articleBadReview = articleBadReview;
	}
	
	
}
