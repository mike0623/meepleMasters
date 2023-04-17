package tw.com.eeit162.meepleMasters.jim.mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.jim.mall.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService pService;

	@GetMapping("/mall/findAllProduct")
	@ResponseBody
	public List<Product> findAllProduct() {
		return pService.findAllProduct();
	}

	@GetMapping("/mall/findProductById/{id}")
	@ResponseBody
	public Product findProductById(@PathVariable(name = "id") Integer id) {
		return pService.findProductById(id);
	}
}
