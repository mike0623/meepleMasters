package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "responseReview")
public class ResponseReview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="responseReviewId")
	private Integer responseReviewId;
	
	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	@Column(name = "fk_responseId")
	private Integer fkResponseId;

	@Column(name = "responseReview")
	private Boolean responseReview;


	public ResponseReview() {
		super();
	}

	public Integer getResponseReviewId() {
		return responseReviewId;
	}

	public void setResponseReviewId(Integer responseReviewId) {
		this.responseReviewId = responseReviewId;
	}

	public Integer getFkMemberId() {
		return fkMemberId;
	}

	public void setFkMemberId(Integer fkMemberId) {
		this.fkMemberId = fkMemberId;
	}

	public Integer getFkResponseId() {
		return fkResponseId;
	}

	public void setFkResponseId(Integer fkResponseId) {
		this.fkResponseId = fkResponseId;
	}

	public Boolean getResponseReview() {
		return responseReview;
	}

	public void setResponseReview(Boolean responseReview) {
		this.responseReview = responseReview;
	}



	
}
