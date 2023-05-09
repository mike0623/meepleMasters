package tw.com.eeit162.meepleMasters.lu.deskBooking.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
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
	                          
	                          HttpSession session, HttpServletRequest request,Model model) {
		
	    // 從 HttpSession 中獲取 memberId

		Member Member = (Member) session.getAttribute("member");
		Integer memberId = Member.getMemberId();
	 
		
		
	    String bookTime = date + " " + time;

	    
	    BookingBean bookingbean = new BookingBean();
	    bookingbean.setBookDeskId(deskId);
	    bookingbean.setBookMemberId(memberId);
	    bookingbean.setBookTime(bookTime);
	    
	    bookingService.insertBooking(bookingbean);

	    
	    // 調用 bookingService 的 insertBooking 方法
	    
	    


	    
	    
	    
	    return "redirect:/booking/success";
	}
	
	
//	@PostMapping("/bookingSubmit")
//	public String bookDesk(@RequestParam("date") String date, @RequestParam("time") String time,
//			@RequestParam("tableIds") List<Integer> tableIds, HttpSession session) {
//
//		// 取得登入的會員ID
////		int memberId = (int) session.getAttribute("memberId");
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
//
//		// 計算每種桌子的價格
//		int type1Price = deskService.getDeskPrice(1);
//		int type2Price = deskService.getDeskPrice(2);
//		int type3Price = deskService.getDeskPrice(3);
//		int totalPrice = type1Count * type1Price + type2Count * type2Price + type3Count * type3Price;
//		
//		// 建立訂位物件並設定屬性
//        BookingDto bookingDto = new BookingDto();
//        bookingDto.setCreatedAt(LocalDate.now());
//        bookingDto.setBookTime(time);
//        bookingDto.setTableIds(Arrays.asList(1, 2, 3));
//        
//        BookingBean bookingBean = bookingService.convertToBookingBean(bookingDto);
////        bookingBean.setTotalPrice(totalPrice);
//
//		// 儲存訂位資料
//        bookingService.insertBooking(bookingBean);
//        
//		return "redirect:/bookingSuccess";
//	}

	// 顯示訂位成功頁面
	@GetMapping("/booking/success")
	public String showBookingSuccessPage(@ModelAttribute("bookingId") int bookingId, Map<String, Object> model) {

		Optional<BookingBean> bookingBean = bookingService.findBookingById(bookingId);
		model.put("bookingBean", bookingBean);
		return "bookingSuccess";
	}
}
