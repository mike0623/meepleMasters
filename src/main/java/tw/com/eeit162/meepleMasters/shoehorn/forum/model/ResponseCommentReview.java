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
	@Column(name="responseCommentReview")
	private Integer responseCommentReview;
	
	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	@Column(name = "fk_responseCommentId")
	private String fkResponseCommentId;
	
	@Column(name = "commentGoodReview")
	private String commentGoodReview;
	
	@Column(name = "commentBadReview")
	private String commentBadReview;

	public ResponseCommentReview() {
		super();
	}

	public Integer getResponseCommentReview() {
		return responseCommentReview;
	}

	public void setResponseCommentReview(Integer responseCommentReview) {
		this.responseCommentReview = responseCommentReview;
	}

	public Integer getFkMemberId() {
		return fkMemberId;
	}

	public void setFkMemberId(Integer fkMemberId) {
		this.fkMemberId = fkMemberId;
	}

	public String getFkResponseCommentId() {
		return fkResponseCommentId;
	}
	
	public void setFkResponseCommentId(String fkResponseCommentId) {
		this.fkResponseCommentId = fkResponseCommentId;
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
