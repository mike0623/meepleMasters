package tw.com.eeit162.meepleMasters.jim.mall.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "favoriteGame")
public class FavoriteGame implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "favoriteGameId")
	private Integer favoriteGameId;

	@Column(name = "fk_memberId")
	private Integer fkMemberId;

	@Column(name = "fk_productId")
	private Integer fkProductId;

	public FavoriteGame() {
	}

	public Integer getFavoriteGameId() {
		return favoriteGameId;
	}

	public void setFavoriteGameId(Integer favoriteGameId) {
		this.favoriteGameId = favoriteGameId;
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

}
