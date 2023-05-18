package tw.com.eeit162.meepleMasters.lu.deskBooking.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "booking")
public class BookingBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookId")
	private Integer bookId;
	
	@Column(name = "fk_bookMemberId")
	private Integer bookMemberId;
	
	@Column(name = "fk_bookDeskId")
	private Integer bookDeskId;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")//HH:mm:ss EEEE
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")//HH:mm:ss
	@Column(name = "createdAt")
	private Date createdAt;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "bookDate")
	private Date bookDate;
	

	@Column(name = "bookTime")
	private String bookTime;
	
	
	public BookingBean() {
		
	}
	@PrePersist
	public void onCreate() {
		if (createdAt == null) {
			createdAt = new Date();
		}
	}
	
	
	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer deskId) {
		this.bookId = deskId;
	}

	public Integer getBookMemberId() {
		return bookMemberId;
	}

	public void setBookMemberId(Integer bookMemberId) {
		this.bookMemberId = bookMemberId;
	}

	public Integer getBookDeskId() {
		return bookDeskId;
	}

	public void setBookDeskId(Integer bookDeskId) {
		this.bookDeskId = bookDeskId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getBookDate() {
		return bookDate;
	}
	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}
	

	public String getBookTime() {
		return bookTime;
	}

	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}
	@Override
	public String toString() {
		return "BookingBean [bookId=" + bookId + ", bookMemberId=" + bookMemberId + ", bookDeskId=" + bookDeskId
				+ ", createdAt=" + createdAt + ", bookDate=" + bookDate + ", bookTime=" + bookTime + "]";
	}
	
	
	
	

}
