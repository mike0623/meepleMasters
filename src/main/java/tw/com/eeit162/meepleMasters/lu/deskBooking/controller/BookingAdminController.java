package tw.com.eeit162.meepleMasters.lu.deskBooking.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
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
		
	
	// 顯示訂位表單頁面
	@GetMapping("/Booking/admin")
	public String adminBookingPage() {

		return "lu/adminBooking";
	}
	
	
	
}

