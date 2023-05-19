package tw.com.eeit162.meepleMasters.page;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.dto.ProductDTO;
import tw.com.eeit162.meepleMasters.jim.mall.service.ProductService;

@Controller
public class PageController {

	@Autowired
	private ProductService pService;

	@GetMapping("/header")
	public String header() {
		return "include/header";
	}

	@GetMapping("/footer")
	public String footer() {
		return "include/footer";
	}

	@GetMapping("/")
	public String home(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "3") Integer count,
			Model model, HttpSession session) {
		Member member = (Member) session.getAttribute("member");

		Integer mID = member != null ? member.getMemberId() : null;

		List<ProductDTO> productList = pService.findAllProduct(page, count, mID).getContent();

		model.addAttribute("productList", productList);

		return "include/index";
	}

	@GetMapping("/admin")
	public String admin() {
		return "include/admin";
	}

}
