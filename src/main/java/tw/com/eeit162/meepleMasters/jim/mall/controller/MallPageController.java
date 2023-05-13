package tw.com.eeit162.meepleMasters.jim.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.jim.mall.service.ProductService;

@Controller
public class MallPageController {

	@Autowired
	private ProductService pService;

	@GetMapping("/mall/addProduct")
	public String addProductPage() {
		return "jim/addProduct";
	}

	@GetMapping("/mall/product")
	public String productPage() {
		return "jim/product";
	}

	@GetMapping("/mall/updateProduct")
	public String updateProductPage(@RequestParam Integer id, Model model) {
		Product product = pService.findProductById(id);

		model.addAttribute("product", product);

		return "jim/updateProduct";
	}

	@GetMapping("/mall/adminProduct")
	public String adminProductPage() {
		return "jim/adminProduct";
	}

}
