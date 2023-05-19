package tw.com.eeit162.meepleMasters.jack.model.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "collectLibrary")
public class CollectLibrary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "collectLibId")
	private Integer collectLibId;

	@Column(name = "fk_memberId")
	private Integer fkMemberId;

	@Column(name = "fk_productId")
	private Integer fkProductId;

	@Column(name = "collectLibBuyTime")
	private Date collectLibBuyTime;

	public CollectLibrary() {
	}

	@PrePersist
	public void onCreate() {
		if (collectLibBuyTime == null) {
			collectLibBuyTime = new Date();
		}
	}

	public Integer getCollectLibId() {
		return collectLibId;
	}

	public void setCollectLibId(Integer collectLibId) {
		this.collectLibId = collectLibId;
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

	public Date getCollectLibBuyTime() {
		return collectLibBuyTime;
	}

	public void setCollectLibBuyTime(Date collectLibBuyTime) {
		this.collectLibBuyTime = collectLibBuyTime;
	}

	@Override
	public String toString() {
		return "CollectLibrary [collectLibId=" + collectLibId + ", fkMemberId=" + fkMemberId + ", fkProductId="
				+ fkProductId + ", collectLibBuyTime=" + collectLibBuyTime + "]";
	}

}
