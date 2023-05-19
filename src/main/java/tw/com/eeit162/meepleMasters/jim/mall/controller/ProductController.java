package tw.com.eeit162.meepleMasters.jim.mall.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.jim.mall.model.dto.ProductDTO;
import tw.com.eeit162.meepleMasters.jim.mall.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService pService;

	// 依照頁數顯示商品
	@GetMapping("/mall/productList")
	@ResponseBody
	public PageImpl<ProductDTO> productList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "6") Integer count, HttpSession session) {
		Member member = (Member) session.getAttribute("member");

		Integer mID = member != null ? member.getMemberId() : null;

		return pService.findAllProduct(page, count, mID);
	}

	// 透過ID尋找商品
	@GetMapping("/mall/findProductById")
	@ResponseBody
	public Product findProductById(@RequestParam Integer id) {
		return pService.findProductById(id);
	}

	// 透過商品名稱尋找商品
	@GetMapping("/mall/findProductByProductName/{productName}")
	@ResponseBody
	public Product findProductByProductName(@PathVariable("productName") String productName) {
		return pService.findProductByProductName(productName);
	}

	// 新增商品
	@PostMapping("/mall/addProduct")
	public String addProduct(@ModelAttribute("product") Product p, @RequestParam(required = false) MultipartFile pImg) {
		if (pImg != null) {
			try {
				BufferedInputStream bis = new BufferedInputStream(pImg.getInputStream());
				p.setProductImg(bis.readAllBytes());
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		pService.addProduct(p);
		return "jim/adminProduct";
	}

	// 透過ID刪除商品
	@DeleteMapping("/mall/deleteProductById")
	@ResponseBody
	public String deleteProductById(@RequestParam Integer id) {
		return pService.deleteProductById(id);
	}

	// 更新商品
	@PostMapping("/mall/updateProduct")
	public String updateProductById(@ModelAttribute Product p, @RequestParam(required = false) MultipartFile pImg)
			throws IOException {
		pService.updateProductById(p, pImg);

		return "jim/adminProduct";
	}

	// 取得圖片
	@GetMapping("/mall/getPhoto")
	@ResponseBody
	public byte[] getProductPhoto(@RequestParam Integer pId) throws IOException {
		Product p = pService.findProductById(pId);

		byte[] pImg = p.getProductImg();
		if (pImg == null || pImg.length == 0) {
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(ResourceUtils.getFile("classpath:\\no_image.png")));
			pImg = bis.readAllBytes();
			bis.close();
		}

		return pImg;
	}

	// 多條件查詢
	@PostMapping("/mall/multiConditionQuery")
	@ResponseBody
	public PageImpl<ProductDTO> multiConditionQuery(@RequestBody String productContent, HttpSession session) {

		Member member = (Member) session.getAttribute("member");

		Integer mID = member != null ? member.getMemberId() : null;
		PageImpl<ProductDTO> pageImpl = pService.multiConditionQuery(1, 6, productContent, mID);

		return pageImpl;
	}
}
