package tw.com.eeit162.meepleMasters.jim.mall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.ShoppingCart;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.OrderDAO;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.ShoppingCartDAO;

@Service
public class ShoppingCartService {

	@Autowired
	ShoppingCartDAO scDAO;

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
		List<ShoppingCart> cartByMember = scDAO.findByMember(new Member(memberId));

		return cartByMember;
	}

	public void cartToOrder(Integer memberId) {
		List<ShoppingCart> cartByMember = scDAO.findByMember(new Member(memberId));

		for (ShoppingCart sc : cartByMember) {
			sc.getProduct();
		}
	}
}
