package tw.com.eeit162.meepleMasters.michael.game.gomoku;

import java.util.ArrayList;

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
		
		while(gomoku.getPlayer1Email() == null) {
			System.out.println("位置還沒印好，請等等");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("gomoku", gomoku);
		if(memberEmail.equals(gomoku.getPlayer1Email())) {
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
				gomoku.setLoser(gomoku.getPlayer2Name());
				jsonObject.put("winnerDegree", gomoku.getPlayer1Degree());
				jsonObject.put("loserDegree", gomoku.getPlayer2Degree());
			}
			if(member.getMemberEmail().equals(gomoku.getPlayer2Email())) {
				gomoku.setWinner(gomoku.getPlayer2Name());
				gomoku.setLoser(gomoku.getPlayer1Name());
				jsonObject.put("winnerDegree", gomoku.getPlayer2Degree());
				jsonObject.put("loserDegree", gomoku.getPlayer1Degree());
			}
			jsonObject.put("endOfTheGame", true);
			jsonObject.put("winner", gomoku.getWinner());
			jsonObject.put("loser", gomoku.getLoser());
			ArrayList<Integer> changeDegreeList = ending(session);
			jsonObject.put("winnerChange", changeDegreeList.get(0));
			jsonObject.put("loserChange", changeDegreeList.get(1));
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
	
	//按下投降按鈕
	@GetMapping("/gomoku/giveUp")
	@ResponseBody
	public String giveUp(HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "gomokuGame");
		jsonObject.put("gameAction", "playChess");
		jsonObject.put("endOfTheGame", true);
		String tableCode = (String)session.getAttribute("tableCode");
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		Gomoku gomoku = (Gomoku)game;
		Member member = (Member)session.getAttribute("member");
		if(member.getMemberEmail().equals(gomoku.getPlayer1Email())) {
			gomoku.setWinner(gomoku.getPlayer2Name());
			gomoku.setLoser(gomoku.getPlayer1Name());
			jsonObject.put("winnerDegree", gomoku.getPlayer1Degree());
			jsonObject.put("loserDegree", gomoku.getPlayer2Degree());
		}
		if(member.getMemberEmail().equals(gomoku.getPlayer2Email())) {
			gomoku.setWinner(gomoku.getPlayer1Name());
			gomoku.setLoser(gomoku.getPlayer2Name());
			jsonObject.put("winnerDegree", gomoku.getPlayer2Degree());
			jsonObject.put("loserDegree", gomoku.getPlayer1Degree());
		}
		jsonObject.put("winner", gomoku.getWinner());
		jsonObject.put("loser", gomoku.getLoser());
		ArrayList<Integer> changeDegreeList = ending(session);
		jsonObject.put("winnerChange", changeDegreeList.get(0));
		jsonObject.put("loserChange", changeDegreeList.get(1));
		
		//ws通知對方
		if(member.getMemberEmail().equals(gomoku.getPlayer1Email())) {
			WebsocketUtil.sendMessageByUserEmail(gomoku.getPlayer2Email(), jsonObject.toString());
		}
		if(member.getMemberEmail().equals(gomoku.getPlayer2Email())) {
			WebsocketUtil.sendMessageByUserEmail(gomoku.getPlayer1Email(), jsonObject.toString());
		}
		
		//刪除session
		session.removeAttribute("tableCode");
		//消滅遊戲桌
		GameRoomUtil.removeGameByTableCode(tableCode);
		
		
		return jsonObject.toString();
	}
	
	//遊戲結束時的方法，回傳改變的degree，前面是贏家，後面是輸家
	public ArrayList<Integer> ending(HttpSession session) {
		String tableCode = (String)session.getAttribute("tableCode");
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		Gomoku gomoku = (Gomoku)game;
		//玩家加減熟練度
		//發遊戲幣給玩家
		Integer averageScore = (500+gomoku.getPlayer1Degree() + gomoku.getPlayer2Degree())/2;
		Member player1 = DataInterface.getMemberByEmail(gomoku.getPlayer1Email());
		Member player2 = DataInterface.getMemberByEmail(gomoku.getPlayer2Email());
		Product product = DataInterface.getProductByProductName(game.getGameName());
		ArrayList<Integer> changeDegreeList = new ArrayList<Integer>();
		if(gomoku.getWinner().equals(gomoku.getPlayer1Name())) {
			Integer winnerChange = DataInterface.updateGameDegreeByBoth(true, averageScore, player1.getMemberId(), product.getProductId());
			Integer loserChange = DataInterface.updateGameDegreeByBoth(false, averageScore, player2.getMemberId(), product.getProductId());
			DataInterface.updateMemberCoin(player1.getMemberId(), 200);
			DataInterface.updateMemberCoin(player2.getMemberId(), 100);
			changeDegreeList.add(winnerChange);
			changeDegreeList.add(loserChange);
		}
		if(gomoku.getWinner().equals(gomoku.getPlayer2Name())) {
			Integer loserChange = DataInterface.updateGameDegreeByBoth(false, averageScore, player1.getMemberId(), product.getProductId());
			Integer winnerChange = DataInterface.updateGameDegreeByBoth(true, averageScore, player2.getMemberId(), product.getProductId());
			DataInterface.updateMemberCoin(player2.getMemberId(), 200);
			DataInterface.updateMemberCoin(player1.getMemberId(), 100);
			changeDegreeList.add(winnerChange);
			changeDegreeList.add(loserChange);
		}
		
		return changeDegreeList;
		
	}
	
	
}
