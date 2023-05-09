package tw.com.eeit162.meepleMasters.michael.game.degree.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
