package tw.com.eeit162.meepleMasters.lu.deskBooking.model;

import java.time.LocalDate;
import java.util.List;

public class BookingDto {
    private LocalDate createdAt;
    private String bookTime;
    private List<Integer> tableIds;
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public String getBookTime() {
		return bookTime;
	}
	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}
	public List<Integer> getTableIds() {
		return tableIds;
	}
	public void setTableIds(List<Integer> tableIds) {
		this.tableIds = tableIds;
	}
    
    


}
