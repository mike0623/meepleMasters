package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "articleCommentReview")
public class ArticleCommentReview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="articleCommentReviewId")
	private Integer articleCommentReviewId;
	
	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	@Column(name = "fk_articleCommentId")
	private Integer fkArticleCommentId;
	
	@Column(name = "commentGoodReview")
	private String commentGoodReview;
	
	@Column(name = "commentBadReview")
	private String commentBadReview;

	public ArticleCommentReview() {
		super();
	}

	public Integer getArticleCommentReviewId() {
		return articleCommentReviewId;
	}

	public void setArticleCommentReviewId(Integer articleCommentReviewId) {
		this.articleCommentReviewId = articleCommentReviewId;
	}

	public Integer getFkMemberId() {
		return fkMemberId;
	}

	public void setFkMemberId(Integer fkMemberId) {
		this.fkMemberId = fkMemberId;
	}

	public Integer getFkArticleCommentId() {
		return fkArticleCommentId;
	}

	public void setFkArticleCommentId(Integer fkArticleCommentId) {
		this.fkArticleCommentId = fkArticleCommentId;
	}

	public String getCommentGoodReview() {
		return commentGoodReview;
	}

	public void setCommentGoodReview(String commentGoodReview) {
		this.commentGoodReview = commentGoodReview;
	}

	public String getCommentBadReview() {
		return commentBadReview;
	}

	public void setCommentBadReview(String commentBadReview) {
		this.commentBadReview = commentBadReview;
	}

}
