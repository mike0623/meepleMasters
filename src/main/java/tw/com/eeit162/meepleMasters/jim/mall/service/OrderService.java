package tw.com.eeit162.meepleMasters.jim.mall.service;

import java.security.SecureRandom;
import java.util.List;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import tw.com.eeit162.meepleMasters.jack.model.bean.CollectLibrary;
import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jack.model.dao.CollectLibraryDao;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Order;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.OrderDetail;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.OrderDAO;

@Service
public class OrderService {

	@Autowired
	private OrderDAO oDAO;

	@Autowired
	private CollectLibraryDao clDAO;

	public Order findByMemberAndOrderStatus(Integer memberId) {
		return oDAO.findByMemberAndOrderStatus(new Member(memberId), "未付款");
	}

	public List<Order> findByMember(Integer memberId) {
		return oDAO.findByMember(new Member(memberId));
	}

	public Order findOrderByOrderId(Integer orderId) {
		return oDAO.findById(orderId).get();
	}

	@Transactional
	public void setPaymentMethod(Integer orderId, String paymentMethod) {
		Order order = oDAO.findById(orderId).get();
		order.setPaymentMethod(paymentMethod);
	}

	@Transactional
	public void setOrderStatus(Integer memberId) {

		Order order = oDAO.findByMemberAndOrderStatus(new Member(memberId), "未付款");
		List<OrderDetail> orderDetails = order.getOrderDetails();

		for (OrderDetail od : orderDetails) {
			CollectLibrary collectLibrary = new CollectLibrary();
			collectLibrary.setFkMemberId(memberId);
			collectLibrary.setFkProductId(od.getProduct().getProductId());
			clDAO.save(collectLibrary);
		}

		order.setOrderStatus("已付款");
	}

	@Autowired
	private ServletContext sContext;

	public String ecPay(Order order) {
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

		List<OrderDetail> orderDetails = order.getOrderDetails();

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
}
