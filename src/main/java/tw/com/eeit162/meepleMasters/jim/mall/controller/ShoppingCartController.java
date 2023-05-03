package tw.com.eeit162.meepleMasters.jim.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.com.eeit162.meepleMasters.jim.mall.service.ShoppingCartService;

@Controller
@RequestMapping(path = "/shoppingCart")
public class ShoppingCartController {

	@Autowired
	ShoppingCartService scService;

	// 依照商品ID及會員ID將商品加入購物車
	@GetMapping("/insertShoppingCart")
	public void insertShoppingCart(@RequestParam Integer productId, @RequestParam Integer memberId) {
		scService.insertShoppingCart(productId, memberId);
	}
}
