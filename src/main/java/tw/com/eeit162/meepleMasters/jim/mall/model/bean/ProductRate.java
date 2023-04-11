package tw.com.eeit162.meepleMasters.jim.mall.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "productRate")
public class ProductRate implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "productRateId")
	private Integer productRateId;

	@Column(name = "fk_productId")
	private Integer fkProductId;

	@Column(name = "evaluation")
	private Integer evaluation;

	@Column(name = "fk_memberId")
	private Integer fkMemberId;

	public ProductRate() {
	}

	public Integer getProductRateId() {
		return productRateId;
	}

	public void setProductRateId(Integer productRateId) {
		this.productRateId = productRateId;
	}

	public Integer getFkProductId() {
		return fkProductId;
	}

	public void setFkProductId(Integer fkProductId) {
		this.fkProductId = fkProductId;
	}

	public Integer getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
	}

	public Integer getFkMemberId() {
		return fkMemberId;
	}

	public void setFkMemberId(Integer fkMemberId) {
		this.fkMemberId = fkMemberId;
	}

}
