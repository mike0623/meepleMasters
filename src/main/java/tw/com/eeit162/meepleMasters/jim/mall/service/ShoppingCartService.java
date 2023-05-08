package tw.com.eeit162.meepleMasters.jim.mall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.ShoppingCart;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.ShoppingCartDAO;

@Service
public class ShoppingCartService {

	@Autowired
	ShoppingCartDAO scDAO;

	public String addShoppingCart(Integer productId, Integer memberId) {

		ShoppingCart sc = scDAO.findByFkMemberIdAndFkProductId(memberId, productId);

		if (sc != null) {
			scDAO.deleteById(sc.getCartId());
			return "cancel";
		}

		ShoppingCart shoppingCartItem = new ShoppingCart();
		shoppingCartItem.setFkMemberId(memberId);
		shoppingCartItem.setFkProductId(productId);
		scDAO.save(shoppingCartItem);

		return "join";
	}

}
