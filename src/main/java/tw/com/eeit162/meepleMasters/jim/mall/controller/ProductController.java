package tw.com.eeit162.meepleMasters.jim.mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.jim.mall.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService pService;

	@GetMapping("/mall/findAllProduct")
	@ResponseBody
	public List<Product> findAllProduct(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "2") Integer count) {
		return pService.findAllProduct(page, count);
	}

	@GetMapping("/mall/findProductById/")
	@ResponseBody
	public Product findProductById(@RequestParam Integer id) {
		return pService.findProductById(id);
	}

	@PutMapping("/mall/insertProduct/")
	public void insertProduct(@RequestParam Product product) {
		pService.insertProduct(product);
	}

	@DeleteMapping("/mall/deleteProductById/")
	public void deleteProductById(@RequestParam Integer id) {
		pService.deleteProductById(id);
	}

	@PutMapping("/mall/updateProductById/")
	public void updateProductById(Product product) {
		pService.updateProductById(product);
	}
}
