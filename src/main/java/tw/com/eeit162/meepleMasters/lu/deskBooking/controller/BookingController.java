package tw.com.eeit162.meepleMasters.lu.deskBooking.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jack.service.MemberService;
import tw.com.eeit162.meepleMasters.lu.deskBooking.model.BookingBean;
import tw.com.eeit162.meepleMasters.lu.deskBooking.model.BookingDto;
import tw.com.eeit162.meepleMasters.lu.deskBooking.model.DeskBean;
import tw.com.eeit162.meepleMasters.lu.deskBooking.service.BookingService;
import tw.com.eeit162.meepleMasters.lu.deskBooking.service.DeskService;

@Controller
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private DeskService deskService;
	
	@Autowired
	private MemberService memberService;
		
	
	// 顯示訂位表單頁面
	@GetMapping("/bookingForm")
	public String showBookingForm(Model model) {
		List<DeskBean> deskList = deskService.findAllDesks();
		BookingBean bookingBean = new BookingBean();
		model.addAttribute("deskList", deskList);
		model.addAttribute("bookingBean", bookingBean);
		return "lu/bookinglist";
	}
	
	// 顯示可選擇的桌號頁面
	@GetMapping("/booking/continue")
	public String showbookingseat(@RequestParam("date") String date,
	                              @RequestParam("time") String time,
	                              HttpSession session) {
	    // 將使用者選擇的日期和時段存入 session 中
	    session.setAttribute("selectedDate", date);
	    session.setAttribute("selectedTime", time);
	    
	    return "lu/bookingseat";
	}
	
	// 顯示所選擇的訂位資訊(日期 時段 桌號)
	@GetMapping("/booking/display")
	public String bookingdisplay(@RequestParam("selectedDate") String date,
	                              @RequestParam("selectedTime") String time,
	                              @RequestParam("deskId") Integer deskId,
	                              HttpSession session, HttpServletRequest request,Model model) {
		
		DeskBean tableType = deskService.getDeskById(deskId);
		
		model.addAttribute("selectedDate", date);
	    model.addAttribute("selectedTime", time);
	    model.addAttribute("tableType", tableType);
	    
	    return "lu/bookingdisplay";
	}
	
	// 送出訂位資料(包含memberID 日期 時段 桌號)
	@PostMapping("/booking/Submit")
	public String booksubmit(@ModelAttribute(name="bookingBean") BookingBean booking,
							  @RequestParam("selectedDate") String date,
	                          @RequestParam("selectedTime") String time,
	                          @RequestParam("deskId") Integer deskId,
	                          
	                          HttpSession session, HttpServletRequest request,Model model) throws ParseException {
		
	    // 從 HttpSession 中獲取 memberId
		Member Member = (Member) session.getAttribute("member");
		Integer memberId = Member.getMemberId();
	 
		// 創建SimpleDateFormat物件，並設定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // 使用SimpleDateFormat的parse()方法將日期字串轉成Date物件
        Date bookDate = sdf.parse(date);
   
	    BookingBean bookingbean = new BookingBean();
	    bookingbean.setBookDeskId(deskId);
	    bookingbean.setBookMemberId(memberId);
	    bookingbean.setBookDate(bookDate);
	    bookingbean.setBookTime(time);
	    
	    bookingService.insertBooking(bookingbean);
	    DeskBean tableType = deskService.getDeskById(deskId);
		
		model.addAttribute("selectedDate", date);
	    model.addAttribute("selectedTime", time);
	    model.addAttribute("tableType", tableType);
	    
	    return "lu/Bookingsuccess";
	}
//	@PostMapping("/bookingSubmit")
//	public String bookDesk(@RequestParam("date") String date, @RequestParam("time") String time,
//			@RequestParam("tableIds") List<Integer> tableIds, HttpSession session) {

//		int memberId =1;
//		// 計算每種桌子的總數量
//		int type1Count = 0;
//		int type2Count = 0;
//		int type3Count = 0;
//		for (Integer tableId : tableIds) {
//			DeskBean desk = deskService.getDeskById(tableId);
//			switch (desk.getDeskType()) {
//			case "2人桌":
//				type1Count++;
//				break;
//			case "4人桌":
//				type2Count++;
//				break;
//			case "5人桌":
//				type3Count++;
//				break;
//			default:
//				break;
//			}
//		}
//		return "redirect:/bookingSuccess";
//	}

	// 顯示訂位成功頁面
//	@GetMapping("/booking/success")
//	public String showBookingSuccessPage(@RequestParam("id") Integer id, Model model    ) {
//
//			Optional<BookingBean> booking = bookingService.findBookingById(id);
//			
//			model.addAttribute("Booking", booking);
//
//
//		return "lu/Bookingsuccess";
//	}
	
	
	//顯示訂位紀錄
	@GetMapping("/booking/record")
	public String showBookingRecord(HttpSession session, HttpServletRequest request,Model model) {
		
		// 從session中取得會員資料
		Member Member = (Member) session.getAttribute("member");
		Integer memberId = Member.getMemberId();
		Member memberName = memberService.findMemberById(memberId);
	
		// 根據memberId取得會員預約紀錄
		List<BookingBean> bookingRecord = bookingService.findAllBookingsByMemberId(memberId);

		// 取出所有預約紀錄中的桌子類型
		List<DeskBean> desks = new ArrayList<>();

		for (BookingBean booking : bookingRecord) {
		    DeskBean desk = deskService.getDeskById(booking.getBookDeskId());
		    if (desk != null) {
		        desks.add(desk);
		    }
		}
		
		model.addAttribute("bookingrecord", bookingRecord);
		model.addAttribute("desks", desks);
		model.addAttribute("memberName", memberName.getMemberName());
		return "lu/bookingrecord";
	}
	
	@ResponseBody
	@DeleteMapping("/booking/delete/{bookId}")
	public String deleteBooking(@PathVariable("bookId") Integer bookId) {
	    bookingService.deleteBookingById(bookId);
	    return "";
	}
	
	
}
