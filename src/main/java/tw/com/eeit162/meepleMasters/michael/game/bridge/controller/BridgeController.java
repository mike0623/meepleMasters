package tw.com.eeit162.meepleMasters.michael.game.bridge.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.michael.game.Game;
import tw.com.eeit162.meepleMasters.michael.game.bridge.Bridge;
import tw.com.eeit162.meepleMasters.michael.game.room.GameRoomUtil;

@Controller
public class BridgeController {
	
	//印撲克牌(0為卡背，1-13為黑桃，14-26為紅心，27-39為方塊，40-52為梅花)
	@GetMapping("/poker/{card}")
	@ResponseBody
	public ResponseEntity<byte[]> printPoker(@PathVariable("card") Integer card,HttpServletRequest request){
		String suits = "back";
		Integer number = 0;
		if(card != 0) {
			Integer testSuits = Math.floorDiv(card, 13);
			Integer testNumber =  card % 13;
			if(testNumber == 0) {
				testSuits -= 1;
				number = 13;
			}
			number = testNumber;
			if(testSuits == 0) {
				suits = "spade";
			}
			if(testSuits == 1) {
				suits = "heart";
			}
			if(testSuits == 2) {
				suits = "diamond";
			}
			if(testSuits == 3) {
				suits = "club";
			}
		}
		
		
		String cardName = suits+number+".png";
		String realPath = request.getServletContext().getRealPath("");
		File file = new File(realPath+"img/michael/game/poker/"+cardName);
		byte[] poker = null;
		try (FileInputStream fis = new FileInputStream(file)){
			poker = fis.readAllBytes();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		
		return new ResponseEntity<byte[]>(poker,headers,HttpStatus.OK);
	}
	
	//進入遊戲的唯一路徑
	@GetMapping("/bridge/enterGameView/{tableCode}/{memberEmail}")
	public String enterGameView(@PathVariable("tableCode") String tableCode,@PathVariable("memberEmail") String memberEmail,Model model) {
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		Bridge bridge = (Bridge)game;
		if(game.getGameStatus() != 3) {
			bridge.initGameSet(game);
			game.setGameStatus(3);
		}
		
		
		model.addAttribute("bridge",bridge);
		
		//model.addAttribute("myself",bridge);
		
		
		return "/michael/game/bridge";
	}
	
	
}
