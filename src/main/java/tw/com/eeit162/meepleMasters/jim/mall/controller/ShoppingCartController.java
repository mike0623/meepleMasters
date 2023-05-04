package tw.com.eeit162.meepleMasters.jim.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ShoppingCart addShoppingCart(@RequestParam Integer productId, @RequestParam Integer memberId) {
		return scService.addShoppingCart(productId, memberId);
	}
}
