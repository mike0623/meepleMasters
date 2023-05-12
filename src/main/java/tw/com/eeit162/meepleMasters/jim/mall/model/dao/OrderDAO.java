package tw.com.eeit162.meepleMasters.jim.mall.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Order;

public interface OrderDAO extends JpaRepository<Order, Integer> {
	Order findByMemberAndOrderStatus(Member m, String os);
}
