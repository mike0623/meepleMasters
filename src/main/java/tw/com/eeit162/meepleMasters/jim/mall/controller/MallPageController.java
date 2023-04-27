package tw.com.eeit162.meepleMasters.jim.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MallPageController {

	@GetMapping("/mall/addProduct")
	public String addProductPage() {
		return "jim/addProduct";
	}
	
	@GetMapping("/mall/product")
	public String product() {
		return "jim/product";
	}
}
