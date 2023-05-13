package tw.com.eeit162.meepleMasters.michael.game.degree.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.michael.game.degree.model.GameDegree;
import tw.com.eeit162.meepleMasters.michael.game.degree.service.GameDegreeService;

@Controller
public class GameDegreeController {
	
	@Autowired
	private GameDegreeService degreeService;
	
	@GetMapping("/GameDegree/findGameDegree/{memberId}/{productId}")
	@ResponseBody
	public GameDegree findGameDegree(@PathVariable("memberId") Integer memberId,@PathVariable("productId") Integer productId) {
		return degreeService.findGameDegreeByBoth(memberId, productId);
	}
	
	//回傳變動幾分(可能有負數)
	@PostMapping("/GameDegree/updateGameDegreeByBoth")
	@ResponseBody
	public Integer updateGameDegreeByBoth(@RequestBody String body) {
		JSONObject json = new JSONObject(body);
		boolean isWin = json.getBoolean("isWin");
		int averageScore = json.getInt("averageScore");
		int memberId = json.getInt("memberId");
		int productId = json.getInt("productId");
		return degreeService.updateGameDegreeByBoth(isWin, averageScore, memberId, productId);
	}
}
