package tw.com.eeit162.meepleMasters.michael.game.gomoku;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tw.com.eeit162.meepleMasters.michael.game.Game;
import tw.com.eeit162.meepleMasters.michael.game.room.GameRoomUtil;

@Controller
public class GomokuController {
	
	//進入遊戲的唯一路徑
	@GetMapping("/gomoku/enterGameView/{tableCode}/{memberEmail}")
	public String enterGameView(@PathVariable("tableCode") String tableCode,@PathVariable("memberEmail") String memberEmail,Model model) {
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
//		System.out.println(memberEmail+"進入了遊戲，遊戲狀態:"+game.getGameStatus());
		Gomoku gomoku = (Gomoku)game;
		if(game.getGameStatus() != 3 && game.getHostEmail().equals(memberEmail)) {
			gomoku.initGameSet(game);
			game.setGameStatus(3);
		}
//		System.out.println(gomoku.getChessBoard().get(new int[5][4]));
		
		
		model.addAttribute("gomoku", gomoku);
		if(gomoku.getPlayer1Email().equals(memberEmail)) {
			model.addAttribute("myself", "player1");
		}else {
			model.addAttribute("myself", "player2");
		}
		
		System.out.println(gomoku);
		
		return "/michael/game/gomoku";
	}
	
	
}
