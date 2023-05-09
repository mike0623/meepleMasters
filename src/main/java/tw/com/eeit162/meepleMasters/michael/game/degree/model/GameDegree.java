package tw.com.eeit162.meepleMasters.michael.game.degree.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gameDegree")
public class GameDegree {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gameDegreeId")
	private Integer gameDegreeId;

	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	@Column(name = "fk_productId")
	private Integer fkProductId;
	
	@Column(name = "score")
	private Integer score;

	public Integer getGameDegreeId() {
		return gameDegreeId;
	}

	public void setGameDegreeId(Integer gameDegreeId) {
		this.gameDegreeId = gameDegreeId;
	}

	public Integer getFkMemberId() {
		return fkMemberId;
	}

	public void setFkMemberId(Integer fkMemberId) {
		this.fkMemberId = fkMemberId;
	}

	public Integer getFkProductId() {
		return fkProductId;
	}

	public void setFkProductId(Integer fkProductId) {
		this.fkProductId = fkProductId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	
}
