package tw.com.eeit162.meepleMasters.michael.message.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="sticker")
public class Sticker implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stickerId")
	private Integer stickerId;
	
	
	@Column(name = "stickerMean")
	private String stickerMean;
	
	@Lob
	@Column(name = "stickerImg")
	private byte[] stickerImg;

	public Sticker() {
	}

	public Integer getStickerId() {
		return stickerId;
	}

	public void setStickerId(Integer stickerId) {
		this.stickerId = stickerId;
	}

	public byte[] getStickerImg() {
		return stickerImg;
	}

	public void setStickerImg(byte[] stickerImg) {
		this.stickerImg = stickerImg;
	}

	public String getStickerMean() {
		return stickerMean;
	}

	public void setStickerMean(String stickerMean) {
		this.stickerMean = stickerMean;
	}
	
	

}
