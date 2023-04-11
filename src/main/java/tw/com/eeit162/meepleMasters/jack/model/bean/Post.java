package tw.com.eeit162.meepleMasters.jack.model.bean;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "postId")
	private Integer postId;
	
	@Column(name = "createTime")
	private Date createTime;
	
	@Column(name = "updateTime")
	private Date updateTime;
	
	@Column(name = "context")
	private String context;
	
	@Column(name = "showImg")
	private byte[] showImg;
	
	@Column(name = "click")
	private Integer click;
	
	@Column(name = "fk_memberId")
	private Integer fkMemberId;
	
	public Post() {
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public byte[] getShowImg() {
		return showImg;
	}

	public void setShowImg(byte[] showImg) {
		this.showImg = showImg;
	}

	public Integer getClick() {
		return click;
	}

	public void setClick(Integer click) {
		this.click = click;
	}

	public Integer getFkMemberId() {
		return fkMemberId;
	}

	public void setFkMemberId(Integer fkMemberId) {
		this.fkMemberId = fkMemberId;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", createTime=" + createTime + ", updateTime=" + updateTime + ", context="
				+ context + ", showImg=" + Arrays.toString(showImg) + ", click=" + click + ", fkMemberId=" + fkMemberId
				+ "]";
	}

}
