package tw.com.eeit162.meepleMasters.lu.deskBooking.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.servlet.ModelAndView;

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
		
		
		
		
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("M月d日");
        List<String> dates = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Date date = calendar.getTime();
            String formattedDate = formatter.format(date);
            dates.add(formattedDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
		
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date minDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 20);
		Date maxDate = calendar.getTime();
		System.out.println(calendar);

	    List<BookingBean> existingBookings = bookingService.findByBookDateAndBookTime(currentDate, "早上");
	    boolean isMorningBooked = existingBookings.size() >= 5;

	    existingBookings = bookingService.findByBookDateAndBookTime(currentDate, "中午");
	    boolean isNoonBooked = existingBookings.size() >= 5;

	    existingBookings = bookingService.findByBookDateAndBookTime(currentDate, "晚上");
	    boolean isEveningBooked = existingBookings.size() >= 5;

        // 計算並顯示接下來7天的日期和時段
     

	    System.out.println(dates);
        model.addAttribute("dates", dates);   
	    model.addAttribute("minDate", minDate);
	    model.addAttribute("maxDate", maxDate);
	    model.addAttribute("isMorningBooked", isMorningBooked);
	    model.addAttribute("isNoonBooked", isNoonBooked);
	    model.addAttribute("isEveningBooked", isEveningBooked);

	    return "lu/bookinglist";
	}
	//非同步判斷
	@GetMapping("/checkAvailability")
	@ResponseBody
	public Map<String, Integer> handleCheckAvailability(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date bookDate,
	        @RequestParam("time") String bookTime) {
	    Map<String, Integer> availabilityMap = bookingService.checkAvailability(bookDate);
	    System.out.println(availabilityMap);
	    return availabilityMap;
	}

	// 顯示可選擇的桌號頁面
	@GetMapping("/booking/continue")
	public String showbookingseat(@RequestParam(name = "date",required = false) String date, @RequestParam(name = "time",required = false) String time,
			HttpSession session,Model model) {
		if(date == "") {
			return "redirect:/bookingForm";
		}
		if(time == null) {
			return "redirect:/bookingForm";
		}
		// 將使用者選擇的日期和時段存入 session 中
		session.setAttribute("selectedDate", date);
		model.addAttribute("selectedTime", time);
		
	    // 在資料庫中查詢是否有相同日期和時段的預約記錄
	    try {
	        // 將日期字串轉換為 java.util.Date 物件
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date bookDate = dateFormat.parse(date);

	        // 在資料庫中查詢是否有相同日期和時段的預約記錄
	        List<BookingBean> bookings = bookingService.findByBookDateAndBookTime(bookDate, time);

	        // 如果有找到對應的預約記錄
	        if (!bookings.isEmpty()) {
	            // 取出該筆資料的桌號
	            List<Integer> bookedDeskIds = new ArrayList<>();
	            for (BookingBean booking : bookings) {
	                bookedDeskIds.add(booking.getBookDeskId());
	            }

	          
	            model.addAttribute("bookedDeskId", bookedDeskIds);
	            System.out.println(bookedDeskIds);
	        }
	    } catch (ParseException e) {
	        // 處理日期轉換失敗的情況
	        e.printStackTrace();
	    }
	    System.out.println(time);
		return "lu/bookingseat";
	}

	// 顯示所選擇的訂位資訊(日期 時段 桌號)
	@GetMapping("/booking/display")
	public String bookingdisplay(@RequestParam(name = "selectedDate",required = false) String date, @RequestParam(name="selectedTime",required = false) String time,
			@RequestParam(name = "deskId",required = false) Integer deskId, HttpSession session, HttpServletRequest request, Model model) {
		
		if(deskId == null ) {
			return "redirect:/booking/continue";
		}
		
		DeskBean tableType = deskService.getDeskById(deskId);

		model.addAttribute("selectedDate", date);
		model.addAttribute("selectedTime", time);
		model.addAttribute("tableType", tableType);
		System.out.println(time);
		return "lu/bookingdisplay";
	}

	// 送出訂位資料(包含memberID 日期 時段 桌號)
	@PostMapping("/booking/Submit")
	public String booksubmit(@ModelAttribute(name = "bookingBean") BookingBean booking,
			@RequestParam("selectedDate") String date, @RequestParam("selectedTime") String time,
			@RequestParam("deskId") Integer deskId,
			HttpSession session, HttpServletRequest request, Model model) throws ParseException {

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

		return "redirect:/booking/record";
	}

	// 顯示訂位紀錄
	@GetMapping("/booking/record")
	public String showBookingRecord(HttpSession session, HttpServletRequest request, Model model) {

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
	
	@GetMapping("/booking")
    public String showBookingPage(Model model) {
        // 計算並顯示接下來7天的日期和時段
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("M月d日");
        List<String> dates = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Date date = calendar.getTime();
            String formattedDate = formatter.format(date);
            dates.add(formattedDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        model.addAttribute("dates", dates);
        System.out.println(dates);
        return "booking-page";
    }
	

}
