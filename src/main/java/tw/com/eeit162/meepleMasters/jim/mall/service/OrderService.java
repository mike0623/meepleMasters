package tw.com.eeit162.meepleMasters.jim.mall.service;

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
}
