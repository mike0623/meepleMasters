package tw.com.eeit162.meepleMasters.jim.mall.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Order;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.OrderDetail;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.ShoppingCart;
import tw.com.eeit162.meepleMasters.jim.mall.service.ShoppingCartService;

@Controller
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService scService;

	// 進入購物車頁面
	@GetMapping("/shoppingCart")
	public String shoppingCartPage(HttpSession session) {
		Member member = (Member) session.getAttribute("member");
		if (member == null) {
			return "jack/loginPage";
		}
		return "jim/shoppingCart";
	}

	// 依照商品ID及會員ID將商品加入購物車
	@GetMapping("/shoppingCart/addShoppingCart")
	@ResponseBody
	public String addShoppingCartAndRemoveItWhenExist(@RequestParam Integer productId,
			@RequestParam(required = false) Integer memberId) {
		return scService.addShoppingCartAndRemoveItWhenExist(productId, memberId);
	}

	// 透過會員ID找到他的購物車
	@GetMapping("/shoppingCart/findShoppingCartByMemberId")
	@ResponseBody
	public List<ShoppingCart> findShoppingCartByMemberId(@RequestParam Integer memberId) {
		List<ShoppingCart> cartByMember = scService.findShoppingCartByMember(memberId);
		return cartByMember;
	}

	// 透過會員ID將購物車轉換成訂單
	@GetMapping("/shoppingCart/cartToOreder/{memberId}")
	public String cartToOrder(@PathVariable Integer memberId, HttpSession session) {
		Order order = scService.cartToOrder(memberId);

		session.setAttribute("order", order);

		List<OrderDetail> orderDetails = order.getOrderDetails();

		session.setAttribute("orderDetails", orderDetails);

		return "jim/order";
	}
}
