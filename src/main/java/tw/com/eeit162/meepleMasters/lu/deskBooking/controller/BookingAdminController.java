package tw.com.eeit162.meepleMasters.lu.deskBooking.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jack.service.MemberService;
import tw.com.eeit162.meepleMasters.lu.deskBooking.model.BookingBean;
import tw.com.eeit162.meepleMasters.lu.deskBooking.model.BookingDto;
import tw.com.eeit162.meepleMasters.lu.deskBooking.model.DeskBean;
import tw.com.eeit162.meepleMasters.lu.deskBooking.service.BookingService;
import tw.com.eeit162.meepleMasters.lu.deskBooking.service.DeskService;

@Controller
public class BookingAdminController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private DeskService deskService;
	
	@Autowired
	private MemberService memberService;
		

	
	//顯示訂位紀錄
	@GetMapping("/Booking/admin")
	public String showBookingRecord(HttpSession session, HttpServletRequest request,Model model) {
		
		
		List<BookingBean> bookingManagement = bookingService.findAllBookings();

		// 取出所有預約紀錄中的<desk>
		List<DeskBean> desks = new ArrayList<>();
		
		// 取出所有預約紀錄中的<member>
		List<Member> members = new ArrayList<>();

		for (BookingBean booking : bookingManagement) {
		    DeskBean desk = deskService.getDeskById(booking.getBookDeskId());
		    if (desk != null) {
		        desks.add(desk);
		    }
		}
		for(BookingBean booking : bookingManagement) {
			Member member = memberService.findMemberById(booking.getBookMemberId());
			if (member != null) {
				members.add(member);
			}
		}
		
		
		model.addAttribute("bookingManagement", bookingManagement);
		model.addAttribute("desks", desks);
		model.addAttribute("members", members);
		
//		model.addAttribute("memberName", memberName.getMemberName());
		return "lu/adminBooking";
	}
	
	// 顯示欲更改內容的訂單
	@GetMapping("/booking/updateForm/{bookId}")
	public String getUpdateForm(@PathVariable("bookId") Integer bookId, Model model) {
	    
		BookingBean bookingUpdate = bookingService.findBookingById(bookId);
		
		Integer deskId = bookingUpdate.getBookDeskId();
		
		Integer memberId = bookingUpdate.getBookMemberId();
		
		String deskType = deskService.getDeskTypeById(deskId);
		
		Member memberName = memberService.findMemberById(memberId);
		
		
		model.addAttribute("bookingUpdate", bookingUpdate);
		model.addAttribute("memberName", memberName);
	    model.addAttribute("deskType", deskType);

	    return "lu/bookingupdate";
	}
	
	//送出更改內容
	@PostMapping("/booking/update")
	public String bookingUpdateById(@ModelAttribute("bookingUpdate") BookingBean bookingbean, Model model) throws IOException {
		// 進行更新操作，例如呼叫相應的服務方法
		bookingService.updateBookingById(bookingbean);
		
		
	    return "redirect:/Booking/admin";
	}
	
	
	
	
}

