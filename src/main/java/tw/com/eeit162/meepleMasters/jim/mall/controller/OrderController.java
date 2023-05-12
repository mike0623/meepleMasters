package tw.com.eeit162.meepleMasters.jim.mall.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Order;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.OrderDetail;
import tw.com.eeit162.meepleMasters.jim.mall.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService oService;

	// 進入未付款訂單時，透過會員ID找到其未結帳訂單
	@GetMapping("/mall/order/")
	public String order(@RequestParam(required = false) Integer memberId, HttpSession session) {

		if (memberId == null) {
			return "jack/loginPage";
		}

		Order order = oService.findOrderByMember(memberId);

		if (order == null) {
			return "include/index";
		} else {
			session.setAttribute("order", order);

			List<OrderDetail> orderDetails = order.getOrderDetails();

			session.setAttribute("orderDetails", orderDetails);
		}

		return "jim/order";
	}

	@Autowired
	private ServletContext sContext;

	@ResponseBody
	@GetMapping("/order/ecpayTest")
	public String test() {
		AllInOne allInOne = new AllInOne("");

		AioCheckOutALL aioCheckOutALL = new AioCheckOutALL();

		aioCheckOutALL.setBidingCard("1");
		aioCheckOutALL.setClientBackURL((String) sContext.getAttribute("root"));
		aioCheckOutALL.setItemName("AAA");
		aioCheckOutALL.setMerchantID("2000132");
		aioCheckOutALL.setMerchantTradeNo("202305120926");
		aioCheckOutALL.setTotalAmount("100");
		aioCheckOutALL.setMerchantTradeDate("2023/05/12 21:11:00");
		aioCheckOutALL.setTradeDesc("TradeDesc");
		aioCheckOutALL.setReturnURL((String) sContext.getAttribute("root"));
		aioCheckOutALL.setMerchantMemberID("162");
	

		String aioCheckOut = allInOne.aioCheckOut(aioCheckOutALL, null);
		return aioCheckOut;
	}

}
