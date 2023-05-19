package tw.com.eeit162.meepleMasters.jim.mall.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jack.service.MemberService;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.FavoriteGameList;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.FavoriteGame;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.jim.mall.service.FavoriteGameService;
import tw.com.eeit162.meepleMasters.jim.mall.service.ProductService;

@Controller
public class FavoriteGameController {

	@Autowired
	private FavoriteGameService fgService;

	@Autowired
	private MemberService mService;

	@Autowired
	private ProductService pService;

	// 判斷資料庫有無進行購物車增加或刪除
	@GetMapping("/favoriteGame/addFavoriteGame")
	@ResponseBody
	public String addFavoriteGameAndRemoveItWhenExist(@RequestParam Integer productId, @RequestParam Integer memberId) {
		return fgService.addFavoriteGameAndRemoveItWhenExist(productId, memberId);
	}

	// 透過會員email找到他的最愛遊戲清單
	@GetMapping("/favoriteGame/favoriteGameList/{memberEmail}")
	@ResponseBody
	public FavoriteGameList favoriteGameList(@PathVariable String memberEmail) {

		Member member = mService.findMemberByEmail(memberEmail);

		List<FavoriteGame> gameList = fgService.findFavoriteGameList(member.getMemberId());

		List<Product> productList = new ArrayList<>();
		for (FavoriteGame fg : gameList) {

			Product product = pService.findProductById(fg.getProduct().getProductId());
			productList.add(product);
		}
		
		FavoriteGameList favoriteGameList = new FavoriteGameList(productList);
		return favoriteGameList;
	}

}
