package tw.com.eeit162.meepleMasters.michael.game.bridge.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.michael.game.Game;
import tw.com.eeit162.meepleMasters.michael.game.bridge.Bridge;
import tw.com.eeit162.meepleMasters.michael.game.bridge.BridgePlayer;
import tw.com.eeit162.meepleMasters.michael.game.room.GameRoomUtil;
import tw.com.eeit162.meepleMasters.michael.util.DataInterface;
import tw.com.eeit162.meepleMasters.michael.websocket.util.WebsocketUtil;

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
			number = testNumber;
			if(testNumber == 0) {
				testSuits -= 1;
				number = 13;
			}
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
//		System.out.println(memberEmail+"進入了遊戲，遊戲狀態:"+game.getGameStatus());
		Bridge bridge = (Bridge)game;
		if(game.getGameStatus() != 3 && game.getHostEmail().equals(memberEmail)) {
			bridge.initGameSet(game);
			game.setGameStatus(3);
		}
		while(bridge.getPlayerSeat().size() == 0) {
			System.out.println("位置還沒印好，請等等");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		BridgePlayer myself = null;
		for(BridgePlayer player: bridge.getPlayerSeat()) {
			if(player.getEmail().equals(memberEmail)) {
//				System.out.println("玩家"+memberEmail+"有進入myself方法");
				myself = player;
			}
		}
		ArrayList<BridgePlayer> myArray = new ArrayList<BridgePlayer>();
		Integer mySeat = bridge.getPlayerSeat().indexOf(myself);
		Integer count = mySeat;
//		System.out.println("玩家"+memberEmail+"的位置是幾號"+mySeat);
		for(int i = 0;i<bridge.getPlayerSeat().size();i++) {
			myArray.add(bridge.getPlayerSeat().get(count));
			if(count == bridge.getPlayerSeat().size()-1) {
				count = 0;
			}else {
				count++;
			}
		}
//		System.out.println("玩家"+memberEmail+"的myArray"+myArray);
		model.addAttribute("tableCode", tableCode);
		model.addAttribute("bridge",bridge);
		model.addAttribute("myArray",myArray);
		
		
		return "/michael/game/bridge";
	}
	
	//按下喊王按鈕時
	@GetMapping("/bridge/bidding/{tableCode}/{memberEmail}/{suit}/{trumpLevel}")
	@ResponseBody
	public String bidding(@PathVariable("tableCode") String tableCode,@PathVariable("memberEmail") String memberEmail,@PathVariable("suit")String suit,@PathVariable("trumpLevel")Integer trumpLevel) {
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		Bridge bridge = (Bridge)game;
		BridgePlayer oldPlayer = bridge.findBridgePlayerByEmail(memberEmail);
		boolean isBiddingPhase = bridge.bidding(oldPlayer,suit,trumpLevel);
		BridgePlayer newPlayer = bridge.nextTurn(oldPlayer,isBiddingPhase);
		if(!isBiddingPhase) {
			//如果結束bidding階段，則改變目前回合玩家
			Integer bidderIndex = bridge.getPlayerSeatIndex(bridge.getPlayerNBid());
			Integer newIndex = 0;
			if(bidderIndex == bridge.getPlayerSeat().size()-1) {
				newIndex = 0;
			}else {
				newIndex = bidderIndex + 1;
			}
			newPlayer = bridge.getPlayerSeat().get(newIndex);
			bridge.setPlayerNTurn(newPlayer);
			bridge.countWinRequirement();
			System.out.println("紅隊勝利所需墩數"+bridge.getTeam1WinRequirement());
			System.out.println("藍隊勝利所需墩數"+bridge.getTeam2WinRequirement());
			System.out.println("誰是得標者: "+bridge.getPlayerNBid());
			System.out.println("誰是新玩家: "+newPlayer);
		}
		bridge.setPhase(newPlayer,isBiddingPhase);
		System.out.println("現在的階段: "+bridge.getPhase());
		System.out.println("現在是誰的回合: "+bridge.getPlayerNTurn());
		//-----------------------------------------------------------------------
		JSONObject jsonObject = new JSONObject();
		JSONObject wsJson = new JSONObject();
		wsJson.put("action", "bridgeGame");
		wsJson.put("gameAction", "pressBidButton");
		wsJson.put("playerNTurnEmail", bridge.getPlayerNTurn().getEmail());
		if(!isBiddingPhase) {
			jsonObject.put("isBiddingPhase", false);
			jsonObject.put("playerNTurn", bridge.getPlayerNTurn().getName());
			jsonObject.put("playerNTurnEmail", bridge.getPlayerNTurn().getEmail());
			String trumpString = bridge.transTrumpFromStringAndInteger(bridge.getTrump());
			jsonObject.put("trumpAndLevel", trumpString+bridge.getTrumpLevel());
			jsonObject.put("team1WinRequirement", bridge.getTeam1WinRequirement());
			jsonObject.put("team2WinRequirement", bridge.getTeam2WinRequirement());
			
			wsJson.put("isBiddingPhase", false);
			wsJson.put("playerNTurn", bridge.getPlayerNTurn().getName());
			wsJson.put("trumpAndLevel", trumpString+bridge.getTrumpLevel());
			wsJson.put("team1WinRequirement", bridge.getTeam1WinRequirement());
			wsJson.put("team2WinRequirement", bridge.getTeam2WinRequirement());
		}
		if(isBiddingPhase) {
			jsonObject.put("isBiddingPhase", true);
			String trumpString = bridge.transTrumpFromStringAndInteger(bridge.getTrump());
			jsonObject.put("trumpAndLevel", trumpString+bridge.getTrumpLevel());
			jsonObject.put("playerNBid", bridge.getPlayerNBid().getName());
			jsonObject.put("playerNTurn", bridge.getPlayerNTurn().getName());
			
			wsJson.put("isBiddingPhase", true);
			wsJson.put("trumpAndLevel", trumpString+bridge.getTrumpLevel());
			wsJson.put("playerNBid", bridge.getPlayerNBid().getName());
			wsJson.put("playerNTurn", bridge.getPlayerNTurn().getName());
		}
		//-----------------------------------------------------------------------
		for(Member member:game.getPlayers()) {
			if(!member.getMemberEmail().equals(memberEmail)) {
				WebsocketUtil.sendMessageByUserEmail(member.getMemberEmail(), wsJson.toString());
			}
		}
		//-----------------------------------------------------------------------
		
		
		return jsonObject.toString();
	}
	
	//出牌時
	@GetMapping("/bridge/useCard/{tableCode}/{memberEmail}/{card}")
	@ResponseBody
	public String useCard(@PathVariable("tableCode") String tableCode,@PathVariable("memberEmail") String memberEmail,@PathVariable("card") Integer card,HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		JSONObject wsJson = new JSONObject();
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		Bridge bridge = (Bridge)game;
		boolean isBiddingPhase = false;
		//如果是本輪第一張牌，就記錄花色
		if(bridge.getPerTurnSuit() == null) {
			bridge.setPerTurnSuit(bridge.getSuitByCard(card));
		}
		BridgePlayer oldPlayer = bridge.findBridgePlayerByEmail(memberEmail);
		bridge.useCard(oldPlayer,card);
		boolean endOfTheTurn = bridge.isEndOfTheTurn();
		BridgePlayer newPlayer = bridge.nextTurn(oldPlayer,isBiddingPhase);
		boolean isTwoPlayerPhaseOne = false;
//		System.out.println("---------------------------------------");
//		System.out.println("是否結束一輪: "+endOfTheTurn);
//		System.out.println("玩家: "+bridge.getPlayer1().getName()+"手牌: "+bridge.getPlayer1().getHandCardList());
//		System.out.println("玩家: "+bridge.getPlayer2().getName()+"手牌: "+bridge.getPlayer2().getHandCardList());
//		System.out.println("玩家: "+bridge.getPlayer3().getName()+"手牌: "+bridge.getPlayer3().getHandCardList());
//		System.out.println("玩家: "+bridge.getPlayer4().getName()+"手牌: "+bridge.getPlayer4().getHandCardList());
//		System.out.println("---------------------------------------");
		if(endOfTheTurn) {
			//設定本輪贏家
			bridge.setPerTurnWinner();
			//判斷這回合是不是phase1
			jsonObject.put("isThisTurnTwoPlayerPhaseOne", false);
			//如果使兩人玩，判斷是否還在慮牌階段，如果是就發牌
			//不是的話就，玩家營下墩數加一，隊伍加一
			if(bridge.getPlayerSeat().size() == 2 && bridge.getDeskList().size() > 0) {
				//贏家獲得的牌
				jsonObject.put("winnerGetCardInTwoPlayerPhaseOne", bridge.getStringByCard(bridge.getForTwoPlayersCard()));
				//這回合是不是phase1
				jsonObject.put("isThisTurnTwoPlayerPhaseOne", true);
				//做完行動後判斷，是否還是第一階段慮牌
				isTwoPlayerPhaseOne = bridge.twoPlayerPhaseOne();
				//回傳新的牌庫數量與展示卡牌
				jsonObject.put("restSizeOfDesk",bridge.getDeskList().size());
				jsonObject.put("newForTwoPlayerCard",bridge.getForTwoPlayersCard());
			}else {
				bridge.plusScoreWhenEndOfTheTurn();
			}
			//清空使用幾張牌的計數器
			bridge.setUsedCardPerTurn(0);
			//清空玩家的已使用牌
			bridge.resetPlayersUsedCard();
			//重置本輪花色
			bridge.setPerTurnSuit(null);
			//設定新的起始玩家
			bridge.setPlayerNTurn(bridge.getPerTurnWinner());
			newPlayer = bridge.getPerTurnWinner();
			//-------------------------------------------------------------
			jsonObject.put("perTurnWinnerName", bridge.getPerTurnWinner().getName());
			jsonObject.put("perTurnWinnerTeam", bridge.getPerTurnWinner().getTeam());
			jsonObject.put("perTurnWinnerWonTricks", bridge.getPerTurnWinner().getWonTricks());
			jsonObject.put("perTurnWinnerEmail", bridge.getPerTurnWinner().getEmail());
//			System.out.println("誰是本輪贏家: "+bridge.getPerTurnWinner().getName());
			
			//判斷是否遊戲結束
			if(bridge.getPlayer1().getHandCardList().size() == 0) {
				bridge.setIsEndOfTheGame(true);
				bridge.makeWinTeam();
				//結束遊戲呼叫的方法，回傳需要回傳的json
				JSONObject InfoWhenEndOfGame = doWhenEnding(session);
				
				return InfoWhenEndOfGame.toString();
			}
		}
		//在起始玩家決定後，設定遊戲階段
		bridge.setPhase(newPlayer,isBiddingPhase);
		//-----------------------------------------------------------------------
		jsonObject.put("isEndOfTheTurn", endOfTheTurn);
		jsonObject.put("playerNTurn", bridge.getPlayerNTurn().getName());
//		System.out.println("輪到誰的回合: "+bridge.getPlayerNTurn().getName());
		jsonObject.put("playerNTurnEmail", bridge.getPlayerNTurn().getEmail());
		//之後還是不是phase1
		jsonObject.put("isTwoPlayerPhaseOne", isTwoPlayerPhaseOne);
		jsonObject.put("isEndOfTheGame", bridge.getIsEndOfTheGame());
		jsonObject.put("team1WonTricks", bridge.getTeam1WonTricks());
		jsonObject.put("team2WonTricks", bridge.getTeam2WonTricks());
		
		
		
		
		//-----------------------------------------------------------------------
		wsJson.put("action", "bridgeGame");
		wsJson.put("gameAction", "useCard");
		
		
		
		for(Member member:game.getPlayers()) {
			if(!member.getMemberEmail().equals(memberEmail)) {
				WebsocketUtil.sendMessageByUserEmail(member.getMemberEmail(), wsJson.toString());
			}
		}
		//-----------------------------------------------------------------------
		return jsonObject.toString();
	}
	
	//放棄遊戲
	@GetMapping("/bridge/giveUpGame")
	@ResponseBody
	public String giveUpGame(HttpSession session) {
		Member member = (Member)session.getAttribute("member");
		String tableCode = (String)session.getAttribute("tableCode");
		System.out.println("放棄遊戲的: 玩家: "+member.getMemberName()+"目前遊戲桌 "+tableCode);
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		Bridge bridge = (Bridge)game;
		
		
		bridge.setIsEndOfTheGame(true);
		Integer teamNum = bridge.findBridgePlayerByEmail(member.getMemberEmail()).getTeam();
		if(teamNum == 1) {
			bridge.setWinTeam("藍隊");
		}else {
			bridge.setWinTeam("紅隊");
		}
		//結束遊戲呼叫的方法，回傳需要回傳的json
		JSONObject InfoWhenEndOfGame = doWhenEnding(session);
		
		return InfoWhenEndOfGame.toString();
	}
	
	//兩人遊戲快轉到牌庫剩一張
	@GetMapping("/bridge/forTwoPlayersfastForward")
	public String forTwoPlayersfastForward(HttpSession session) {
		String tableCode = (String)session.getAttribute("tableCode");
		Member member = (Member)session.getAttribute("member");
		System.out.println("快轉遊戲的: 玩家: "+member.getMemberName()+"目前遊戲桌 "+tableCode);
		String memberEmail = member.getMemberEmail();
		
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		Bridge bridge = (Bridge)game;
		if(bridge.getDeskList().size() > 0) {
			bridge.forTwoPlayersfastForward();
		}
		
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "bridgeGame");
		jsonObject.put("gameAction", "forTwoPlayersfastForward");
		for(BridgePlayer player:bridge.getPlayerSeat()) {
			if(!player.getEmail().equals(memberEmail)) {
				WebsocketUtil.sendMessageByUserEmail(player.getEmail(), jsonObject.toString());
			}
		}
		
		return "redirect:/bridge/enterGameView/"+tableCode+"/"+memberEmail;
	}
	
	//遊戲結束時做的事情，方法非controller
	public JSONObject doWhenEnding(HttpSession session) {
		String tableCode = (String)session.getAttribute("tableCode");
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		Bridge bridge = (Bridge)game;
		//玩家加減熟練度
		//發遊戲幣給玩家
		Product product = DataInterface.getProductByProductName(game.getGameName());
		Integer winTeamInt = 0;
		if("紅隊".equals(bridge.getWinTeam())) {
			winTeamInt = 1;
		}
		if("藍隊".equals(bridge.getWinTeam())) {
			winTeamInt = 2;
		}
		Integer averageScore = 500;
		for(BridgePlayer player:bridge.getPlayerSeat()) {
			averageScore += player.getBridgeDegree();
		}
		averageScore = (int)Math.ceil(averageScore/game.getFinalNumOfPlayer());
		for(BridgePlayer player:bridge.getPlayerSeat()) {
			Member member = DataInterface.getMemberByEmail(player.getEmail());
			if(player.getTeam() == winTeamInt) {
				Integer changeScore = DataInterface.updateGameDegreeByBoth(true, averageScore, member.getMemberId(), product.getProductId());
				player.setChangeScore(changeScore);
				DataInterface.updateMemberCoin(member.getMemberId(),200);
			}
			if(player.getTeam() != winTeamInt) {
				Integer changeScore = DataInterface.updateGameDegreeByBoth(false, averageScore, member.getMemberId(), product.getProductId());
				player.setChangeScore(changeScore);
				DataInterface.updateMemberCoin(member.getMemberId(),100);
			}
		}
		//遊戲結束直接回傳結束資訊
		JSONObject InfoWhenEndOfGame =  bridge.InfoWhenEndOfGame();
		//通知ws
		Member member = (Member)session.getAttribute("member");
		for(BridgePlayer player:bridge.getPlayerSeat()) {
			if(!player.getEmail().equals(member.getMemberEmail())) {
				WebsocketUtil.sendMessageByUserEmail(player.getEmail(), InfoWhenEndOfGame.toString());
			}
		}
		//消滅session
		session.removeAttribute("tableCode");
		//消滅遊戲桌
		GameRoomUtil.removeGameByTableCode(tableCode);
		
		return InfoWhenEndOfGame;
	}
	
	
}
