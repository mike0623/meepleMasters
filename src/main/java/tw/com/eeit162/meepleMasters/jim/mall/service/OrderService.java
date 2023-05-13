package tw.com.eeit162.meepleMasters.jim.mall.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Order;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.OrderDAO;

@Service
public class OrderService {

	@Autowired
	private OrderDAO oDAO;

	public Order findOrderByMember(Integer memberId) {
		return oDAO.findByMemberAndOrderStatus(new Member(memberId), "未付款");
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
	public void setOrderStatus(Integer orderId) {
		Order order = oDAO.findById(orderId).get();
		order.setOrderStatus("已付款");
	}
}
