package tw.com.eeit162.meepleMasters.michael.game.gomoku;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.michael.game.Game;
import tw.com.eeit162.meepleMasters.michael.game.bridge.Bridge;
import tw.com.eeit162.meepleMasters.michael.game.room.GameRoomUtil;
import tw.com.eeit162.meepleMasters.michael.util.DataInterface;
import tw.com.eeit162.meepleMasters.michael.websocket.util.WebsocketUtil;

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
	
	//下棋
	@GetMapping("/gomoku/action/{x}/{y}")
	@ResponseBody
	public String action(@PathVariable("x") Integer x,@PathVariable("y") Integer y,HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "gomokuGame");
		jsonObject.put("gameAction", "playChess");
		jsonObject.put("endOfTheGame", false);
		Member member = (Member)session.getAttribute("member");
		String tableCode = (String)session.getAttribute("tableCode");
		//改變棋子顏色
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		Gomoku gomoku = (Gomoku)game;
		if(member.getMemberEmail().equals(gomoku.getPlayer1Email())) {
			gomoku.getChessBoard().put("x"+x+"y"+y, gomoku.getPlayer1Color());
			System.out.println("顏色"+gomoku.getChessBoard().get("x"+x+"y"+y));
		}
		if(member.getMemberEmail().equals(gomoku.getPlayer2Email())) {
			gomoku.getChessBoard().put("x"+x+"y"+y, gomoku.getPlayer2Color());
			System.out.println("顏色"+gomoku.getChessBoard().get("x"+x+"y"+y));
		}
		//換人下棋
		gomoku.changePlayerTurn(member.getMemberEmail());
		//製作json
		jsonObject.put("x", x);
		jsonObject.put("y", y);
		jsonObject.put("color", gomoku.getColorByPosition(x, y));
		if(gomoku.getPlayerNTurn().equals(gomoku.getPlayer1Email())) {
			jsonObject.put("playerNTurn", gomoku.getPlayer1Name());
		}
		if(gomoku.getPlayerNTurn().equals(gomoku.getPlayer2Email())) {
			jsonObject.put("playerNTurn", gomoku.getPlayer2Name());
		}
		
		//判斷是否獲勝
		boolean isWin = gomoku.isWin(x,y);
		if(isWin) {
			gomoku.setEndOfTheGame(isWin);
			if(member.getMemberEmail().equals(gomoku.getPlayer1Email())) {
				gomoku.setWinner(gomoku.getPlayer1Name());
			}
			if(member.getMemberEmail().equals(gomoku.getPlayer2Email())) {
				gomoku.setWinner(gomoku.getPlayer2Name());
			}
			jsonObject.put("endOfTheGame", true);
			jsonObject.put("winner", gomoku.getWinner());
			ending(session);
		}
		
		//ws通知對方
		if(member.getMemberEmail().equals(gomoku.getPlayer1Email())) {
			WebsocketUtil.sendMessageByUserEmail(gomoku.getPlayer2Email(), jsonObject.toString());
		}
		if(member.getMemberEmail().equals(gomoku.getPlayer2Email())) {
			WebsocketUtil.sendMessageByUserEmail(gomoku.getPlayer1Email(), jsonObject.toString());
		}
		if(isWin) {
			//刪除session
			session.removeAttribute("tableCode");
			//消滅遊戲桌
			GameRoomUtil.removeGameByTableCode(tableCode);
		}
		
		return jsonObject.toString();
	}
	
	//遊戲結束時的方法
	public void ending(HttpSession session) {
		String tableCode = (String)session.getAttribute("tableCode");
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		Gomoku gomoku = (Gomoku)game;
		//玩家加減熟練度
		//發遊戲幣給玩家
		Product product = DataInterface.getProductByProductName(game.getGameName());
		
		
	}
	
	
}
