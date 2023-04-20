package tw.com.eeit162.meepleMasters.jim.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductPageController {

	@GetMapping("/mall/product")
	public String productPage() {
		return "/jim/Product";
	}
}
