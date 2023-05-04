package tw.com.eeit162.meepleMasters.jim.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.FavoriteGame;
import tw.com.eeit162.meepleMasters.jim.mall.service.FavoriteGameService;

@Controller
public class FavoriteGameController {

	@Autowired
	private FavoriteGameService fgService;

	@GetMapping("/favoriteGame/addFavoriteGame")
	@ResponseBody
	public FavoriteGame addFavoriteGame(@RequestParam Integer productId, @RequestParam Integer memberId) {
		return fgService.addFavoriteGame(productId, memberId);
	}

}
