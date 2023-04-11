package tw.com.eeit162.meepleMasters.jack.model.bean;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "postImg")
public class PostImg {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "postImgId")
	private Integer postImgId;
	
	@Column(name = "postImg")
	private byte[] postImg;
	
	@Column(name = "fk_postId")
	private Integer fkPostId;
	
	public PostImg() {
	}

	public Integer getPostImgId() {
		return postImgId;
	}

	public void setPostImgId(Integer postImgId) {
		this.postImgId = postImgId;
	}

	public byte[] getPostImg() {
		return postImg;
	}

	public void setPostImg(byte[] postImg) {
		this.postImg = postImg;
	}

	public Integer getFkPostId() {
		return fkPostId;
	}

	public void setFkPostId(Integer fkPostId) {
		this.fkPostId = fkPostId;
	}

	@Override
	public String toString() {
		return "PostImg [postImgId=" + postImgId + ", postImg=" + Arrays.toString(postImg) + ", fkPostId=" + fkPostId
				+ "]";
	}

}
