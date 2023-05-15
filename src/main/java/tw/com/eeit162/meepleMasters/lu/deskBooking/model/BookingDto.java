package tw.com.eeit162.meepleMasters.lu.deskBooking.model;


import java.util.Date;
import java.util.List;

public class BookingDto {
	
	private String memberName;
    private String deskType;
    private Integer bookDeskId;
    private Date bookDate;
    private String bookTime;
    
   
    
    
    public Integer getBookDeskId() {
		return bookDeskId;
	}
	public void setBookDeskId(Integer bookDeskId) {
		this.bookDeskId = bookDeskId;
	}

	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getDeskType() {
		return deskType;
	}
	public void setDeskType(String deskType) {
		this.deskType = deskType;
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



    
    


}
