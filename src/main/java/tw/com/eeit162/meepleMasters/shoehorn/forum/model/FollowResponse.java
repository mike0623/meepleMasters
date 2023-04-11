package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "followResponse")
public class FollowResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="followResponseId")
	private Integer followResponseId;
	
	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	@Column(name = "fk_responseId")
	private Integer fkResponseId;

	public FollowResponse() {
		super();
	}

	public Integer getFollowResponseId() {
		return followResponseId;
	}

	public void setFollowResponseId(Integer followResponseId) {
		this.followResponseId = followResponseId;
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
	
	
}
