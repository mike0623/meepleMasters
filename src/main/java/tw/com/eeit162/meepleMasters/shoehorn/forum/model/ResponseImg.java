package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "responseImg")
public class ResponseImg {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="responseImg")
	private Integer responseImg;
	
	@Column(name = "fk_responseId")
	private Integer fkResponseId;
	
	@Lob
	@Column(name = "img")
	private byte[] img;
	
	public ResponseImg() {
		super();
	}
	
	public Integer getResponseImg() {
		return responseImg;
	}

	public void setResponseImg(Integer responseImg) {
		this.responseImg = responseImg;
	}

	public Integer getFkResponseId() {
		return fkResponseId;
	}

	public void setFkResponseId(Integer fkResponseId) {
		this.fkResponseId = fkResponseId;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

}
