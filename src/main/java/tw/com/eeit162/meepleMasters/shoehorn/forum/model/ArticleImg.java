package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "articleImg")
public class ArticleImg {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="articleImg")
	private Integer articleImg;
	
	@Column(name = "fk_articleId")
	private Integer fkArticleId;
	
	@Lob
	@Column(name = "Img")
	private byte[] img;

	public ArticleImg() {
		super();
	}

	public Integer getArticleImg() {
		return articleImg;
	}

	public void setArticleImg(Integer articleImg) {
		this.articleImg = articleImg;
	}

	public Integer getFkArticleId() {
		return fkArticleId;
	}

	public void setFkArticleId(Integer fkArticleId) {
		this.fkArticleId = fkArticleId;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

}
