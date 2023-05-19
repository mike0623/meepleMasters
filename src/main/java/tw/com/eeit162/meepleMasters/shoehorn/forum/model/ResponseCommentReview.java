package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "responseCommentReview")
public class ResponseCommentReview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="responseCommentReviewId")
	private Integer responseCommentReviewId;
	
	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	@Column(name = "fk_responseCommentId")
	private Integer fkResponseCommentId;
	
	@Column(name = "commentReview")
	private Boolean commentReview;


	public ResponseCommentReview() {
		super();
	}

	public Integer getResponseCommentReviewId() {
		return responseCommentReviewId;
	}

	public void setResponseCommentReviewId(Integer responseCommentReviewId) {
		this.responseCommentReviewId = responseCommentReviewId;
	}

	public Integer getFkMemberId() {
		return fkMemberId;
	}

	public void setFkMemberId(Integer fkMemberId) {
		this.fkMemberId = fkMemberId;
	}

	public Integer getFkResponseCommentId() {
		return fkResponseCommentId;
	}
	
	public void setFkResponseCommentId(Integer fkResponseCommentId) {
		this.fkResponseCommentId = fkResponseCommentId;
	}

	public Boolean getCommentReview() {
		return commentReview;
	}

	public void setCommentReview(Boolean commentReview) {
		this.commentReview = commentReview;
	}
	


}
