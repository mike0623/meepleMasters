package tw.com.eeit162.meepleMasters.jim.mall.controller;

import java.security.SecureRandom;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Order;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.OrderDetail;
import tw.com.eeit162.meepleMasters.jim.mall.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService oService;

	// 進入未付款訂單時，透過會員ID找到其未結帳訂單
	@GetMapping("/order/")
	public String order(HttpSession session) {

		Member member = (Member) session.getAttribute("member");

		if (member == null) {
			return "jack/loginPage";
		}

		Order order = oService.findOrderByMember(member.getMemberId());

		if (order == null) {
			return "jim/product";
		} else {
			session.setAttribute("order", order);

			List<OrderDetail> orderDetails = order.getOrderDetails();

			session.setAttribute("orderDetails", orderDetails);
		}

		return "jim/order";
	}

	@Autowired
	private ServletContext sContext;

	// 透過綠界API產生訂單頁面
	@GetMapping("/order/ecPay")
	@ResponseBody
	public String ecPay(HttpSession session) {
		AllInOne allInOne = new AllInOne("");
		AioCheckOutALL aioCheckOutALL = new AioCheckOutALL();

		// 使用者按返回商店後導向的頁面
		aioCheckOutALL.setClientBackURL(
				"http://localhost:8080" + (String) sContext.getAttribute("root") + "/order/checkoutComplete");

		// 隨機生成英數字混和字串
		String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder(20);
		for (int i = 0; i < 20; i++) {
			int randomIndex = new SecureRandom().nextInt(symbols.length());
			char randomChar = symbols.charAt(randomIndex);
			sb.append(randomChar);
		}
		System.out.println(sb);
		// 商店交易編號，唯一值不可重複
		aioCheckOutALL.setMerchantTradeNo(sb.toString());

		Order order = (Order) session.getAttribute("order");
		List<OrderDetail> orderDetails = order.getOrderDetails();
		oService.setPaymentMethod(order.getOrderId(), "綠界");

		String itemName = "";
		Integer totalAmount = 0;

		for (OrderDetail od : orderDetails) {
			itemName += od.getProduct().getProductName() + "#";
			totalAmount += od.getProduct().getProductPrice();
		}
		// 將商品名稱組成字串再set
		aioCheckOutALL.setItemName(itemName);

		// 將商品金額加總後轉成字串
		aioCheckOutALL.setTotalAmount(totalAmount.toString());

		aioCheckOutALL.setMerchantMemberID(order.getMember().getMemberId().toString());
		aioCheckOutALL.setBidingCard("1");
		aioCheckOutALL.setMerchantID("2000132");
		aioCheckOutALL.setMerchantTradeDate("2023/05/12 21:11:00");
		aioCheckOutALL.setTradeDesc("這邊是商品描述喔");
		aioCheckOutALL.setReturnURL((String) sContext.getAttribute("root"));

		String aioCheckOut = allInOne.aioCheckOut(aioCheckOutALL, null);
		return aioCheckOut;
	}

	@GetMapping("/order/checkoutComplete")
	public String checkoutComplete(HttpSession session) {
		Order order = (Order) session.getAttribute("order");

		oService.setOrderStatus(order.getOrderId());

		return "include/index";
	}

}
