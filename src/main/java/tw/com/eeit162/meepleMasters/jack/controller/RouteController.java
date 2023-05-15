package tw.com.eeit162.meepleMasters.jack.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jack.service.CollectLibraryService;
import tw.com.eeit162.meepleMasters.jack.service.MemberService;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;

@Controller
public class RouteController {
	
	@Autowired
	private CollectLibraryService collectLibraryService;
	@Autowired
	private MemberService memberService;
	
	@GetMapping({"/index"})
	public String index() {
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "jack/loginPage";
	}
	
	@GetMapping("/register")
	public String register() {
		
		return "jack/createMember"; 
	}
	
	@GetMapping("/member/admin")
	public String adminPage() {
		
		return "include/admin";
	}
	
	@GetMapping("/member/member")
	public String memberPage() {
		
		return "jack/memberPage";
	}
	
	@GetMapping("/member/game")
	public String gamePage(Model model, HttpSession session) {
		
		Member attribute = (Member) session.getAttribute("member");
		Integer id = attribute.getMemberId();
		
		List<Object[]> collect = collectLibraryService.findMemberCollect(id);
		ArrayList<Product> productList = new ArrayList<>();

		for (int i = 0; i < collect.size(); i++) {
//			System.out.println(collect.get(i));
//			System.out.println("-------------------------");
//			System.out.println(collect.get(i)[0]);
//			System.out.println("-------------------------");
//			System.out.println(collect.get(i)[2]);
			JSONObject jsonObject = new JSONObject(collect.get(i)[2]);
			
//			productList.add(jsonObject.getString("productName"));
			Product product = new Product();
			product.setProductName(jsonObject.getString("productName"));
			product.setProductId(jsonObject.getInt("productId"));
			productList.add(product);
			
			System.out.println(jsonObject.getString("productName"));
			System.out.println("-------------------------");
			System.out.println(productList);

		}
		model.addAttribute("memberProduct", productList);
		
		return "jack/memberProduct";
	}
	
//	@GetMapping("/member/myProfile")
//	public String myProfile() {
//		
//		
//		return "jack/myProfile";
//	}
	
	@GetMapping("/member/profile")
	public String profile() {
		
		
		return "jack/profile";
	}
	
//	@GetMapping("/member/myProfile")
//	public String myProfile(HttpSession session, Model model) {
//		Member sessionMember = (Member) session.getAttribute("member");
//		
//		model.addAttribute("findMember",sessionMember);
//		return "jack/otherMember";
//	}
	
	@GetMapping("/member/myProfile/{id}")
	public String otherMember(@PathVariable("id") Integer memberId, Model model) {
		
		Optional<Member> member = Optional.ofNullable(memberService.findMemberById(memberId));
		
		if(member != null) {
			
			model.addAttribute("findMember", member.get());
		}
		
		return "jack/myProfile";
	}
	
	@GetMapping("/admin/memberManage")
	public String adminMember() {
		
		Optional<List<Member>> option = memberService.findAllMember();
		List<Member> allMember = option.get();
		ArrayList<Member> memberList = new ArrayList<>();
		for(int i = 0; i< allMember.size(); i++) {
			System.out.println(allMember);
			Member member = new Member();
			
			
		}
		
		
		return "jack/memberManage";
	}
	
	

}	
