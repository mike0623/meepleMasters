package tw.com.eeit162.meepleMasters.michael.game.bridge.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

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
import tw.com.eeit162.meepleMasters.michael.game.Game;
import tw.com.eeit162.meepleMasters.michael.game.bridge.Bridge;
import tw.com.eeit162.meepleMasters.michael.game.bridge.BridgePlayer;
import tw.com.eeit162.meepleMasters.michael.game.room.GameRoomUtil;
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
		
		
		
		for(Member member:game.getPlayers()) {
			if(!member.getMemberEmail().equals(memberEmail)) {
				System.out.println("有發訊息給"+member.getMemberEmail());
				WebsocketUtil.sendMessageByUserEmail(member.getMemberEmail(), wsJson.toString());
			}
		}
		
		return jsonObject.toString();
	}
	
	
}
