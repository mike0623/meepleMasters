package tw.com.eeit162.meepleMasters.jim.mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.ShoppingCart;
import tw.com.eeit162.meepleMasters.jim.mall.service.ShoppingCartService;

@Controller
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService scService;

	// 依照商品ID及會員ID將商品加入購物車
	@GetMapping("/shoppingCart/addShoppingCart")
	@ResponseBody
	public String addShoppingCartAndRemoveItWhenExist(@RequestParam Integer productId, @RequestParam Integer memberId) {
		return scService.addShoppingCartAndRemoveItWhenExist(productId, memberId);
	}

	// 透過會員ID找到他的購物車
	@GetMapping("/shoppingCart/findShoppingCartByMemberId")
	@ResponseBody
	public List<ShoppingCart> findShoppingCartByMemberId(@RequestParam Integer memberId) {
		List<ShoppingCart> cartByMember = scService.findShoppingCartByMember(memberId);
		return cartByMember;
	}

	@GetMapping("/shoppingCart/cartToOreder/{memberId}")
	public void cartToOrder(@PathVariable Integer memberId) {
		scService.cartToOrder(memberId);
	}
}
