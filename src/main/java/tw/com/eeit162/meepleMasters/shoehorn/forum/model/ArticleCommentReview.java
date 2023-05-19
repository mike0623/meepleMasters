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
	
	@Column(name = "commentReview")
	private Boolean commentReview;

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

	public Boolean getCommentReview() {
		return commentReview;
	}

	public void setCommentReview(Boolean commentReview) {
		this.commentReview = commentReview;
	}



}
