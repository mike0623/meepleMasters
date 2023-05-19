package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "followArticle")
public class FollowArticle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="followArticleId")
	private Integer followArticletId;
	
	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	@Column(name = "fk_articleId")
	private Integer fkArticleId;

	public FollowArticle() {
		super();
	}

	public Integer getFollowArticletId() {
		return followArticletId;
	}

	public void setFollowArticletId(Integer followArticletId) {
		this.followArticletId = followArticletId;
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
	
	
}
