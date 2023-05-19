package tw.com.eeit162.meepleMasters.jim.mall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Order;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.OrderDetail;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.ShoppingCart;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.OrderDAO;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.OrderDetailDAO;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.ShoppingCartDAO;

@Service
public class ShoppingCartService {

	@Autowired
	ShoppingCartDAO scDAO;

	@Autowired
	OrderDetailDAO odDAO;

	@Autowired
	OrderDAO oDAO;

	public String addShoppingCartAndRemoveItWhenExist(Integer productId, Integer memberId) {

		ShoppingCart shoppingCartItem = new ShoppingCart();
		Member m = new Member(memberId);
		Product p = new Product(productId);
		ShoppingCart sc = scDAO.findByMemberAndProduct(m, p);

		if (sc != null) {
			scDAO.deleteById(sc.getCartId());
			return "remove";
		}

		shoppingCartItem.setMember(m);
		shoppingCartItem.setProduct(p);

		scDAO.save(shoppingCartItem);

		return "join";
	}

	public List<ShoppingCart> findShoppingCartByMember(Integer memberId) {
		return scDAO.findByMember(new Member(memberId));
	}

	public Order cartToOrder(Integer memberId) {

		Member member = new Member(memberId);
		List<ShoppingCart> cartByMember = scDAO.findByMember(member);

		Integer totalPrice = 0;

		Order order = new Order();
		order.setMember(member);

		List<OrderDetail> orderDetails = new ArrayList<>();

		for (ShoppingCart sc : cartByMember) {
			OrderDetail od = new OrderDetail();
			Product product = sc.getProduct();
			od.setProduct(product);
			od.setOrder(order);

			scDAO.deleteById(sc.getCartId());

			orderDetails.add(od);
			totalPrice += product.getProductPrice();
		}
		order.setOrderDetails(orderDetails);
		order.setTotalPrice(totalPrice);

		return oDAO.save(order);
	}
}
