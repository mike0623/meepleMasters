package tw.com.eeit162.meepleMasters.lu.deskBooking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "desk")
public class DeskBean {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "deskId")
	private Integer deskId;
   
	@Column(name = "deskType")
	private String deskType;
	
	@Column(name = "deskNumber")
	private Integer deskNumber;
	
	@Column(name = "deskPrice")
	private Integer deskPrice;

	
	public Integer getDeskId() {
		return deskId;
	}

	public void setDeskId(Integer deskId) {
		this.deskId = deskId;
	}

	public String getDeskType() {
		return deskType;
	}

	public void setDeskType(String deskType) {
		this.deskType = deskType;
	}

	public Integer getDeskNumber() {
		return deskNumber;
	}

	public void setDeskNumber(Integer deskNumber) {
		this.deskNumber = deskNumber;
	}

	public Integer getDeskPrice() {
		return deskPrice;
	}
	
	public void setDeskPrice(Integer deskPrice) {
		this.deskPrice = deskPrice;
	}
	
	
}
