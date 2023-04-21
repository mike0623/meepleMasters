package tw.com.eeit162.meepleMasters.jim.mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.jim.mall.service.ProductService;

@Controller
@RequestMapping(path = "/mall")
public class ProductController {

	@Autowired
	private ProductService pService;

	@GetMapping("/findAllProduct")
	public String findAllProduct(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "6") Integer count, Model model) {
		List<Product> allProduct = pService.findAllProduct(page, count);

		model.addAttribute("allProduct", allProduct);

		return "jim/product";
	}

	@GetMapping("/findProductById")
	@ResponseBody
	public Product findProductById(@RequestParam Integer id) {
		return pService.findProductById(id);
	}

	@PutMapping("/insertProduct")
	@ResponseBody
	public Product insertProduct(@RequestBody String body, MultipartFile productImg) {
		return pService.insertProduct(body);
	}

	@DeleteMapping("/deleteProductById")
	@ResponseBody
	public String deleteProductById(@RequestParam Integer id) {
		if (pService.findProductById(id) != null) {
			pService.deleteProductById(id);
			return "刪除成功";
		}
		return "刪除失敗";
	}

	@PutMapping("/updateProductById")
	@ResponseBody
	public String updateProductById(@RequestParam Integer id, @RequestBody String body) {
		if (pService.findProductById(id) != null) {
			pService.updateProductById(body);
			return "修改成功";
		}
		return "修改失敗";
	}
}
