package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "faviroteArticle")
public class FaviroteArticle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="faviroteArticleId")
	private Integer faviroteArticleId;
	
	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	@Column(name = "fk_articleId")
	private Integer fkArticleId;

	public FaviroteArticle() {
		super();
	}

	public Integer getFaviroteArticleId() {
		return faviroteArticleId;
	}

	public void setFaviroteArticleId(Integer faviroteArticleId) {
		this.faviroteArticleId = faviroteArticleId;
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
