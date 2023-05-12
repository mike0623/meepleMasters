package tw.com.eeit162.meepleMasters.jack.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jack.service.CollectLibraryService;

@Controller
public class CollectLibraryController {

	@Autowired
	private CollectLibraryService collectLibraryService;

//	@ResponseBody
//	@GetMapping("/findMemberCollect/{id}")
//	public ArrayList<String> findMemberCollect(@PathVariable(name = "id") Integer memberId, Model model) {
//		
//		List<Object[]> collect = collectLibraryService.findMemberCollect(memberId);
//		ArrayList<String> productList = new ArrayList<>();
//
//		for (int i = 0; i < collect.size(); i++) {
//			System.out.println(collect.get(i));
//			System.out.println("-------------------------");
//			System.out.println(collect.get(i)[0]);
//			System.out.println("-------------------------");
//			System.out.println(collect.get(i)[2]);
//			JSONObject jsonObject = new JSONObject(collect.get(i)[2]);
//			productList.add(jsonObject.getString("productName"));
//			System.out.println(jsonObject.getString("productName"));
//			System.out.println("-------------------------");
//			System.out.println(productList);
//
//		}
//		model.addAttribute("memberProduct", productList);
//
//		return productList;
//	}
}
