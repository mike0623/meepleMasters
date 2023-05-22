package tw.com.eeit162.meepleMasters.michael.game.room;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.michael.game.Game;
import tw.com.eeit162.meepleMasters.michael.game.bridge.Bridge;
import tw.com.eeit162.meepleMasters.michael.game.gomoku.Gomoku;
import tw.com.eeit162.meepleMasters.michael.util.DataInterface;
import tw.com.eeit162.meepleMasters.michael.util.DateTimeConverter;
import tw.com.eeit162.meepleMasters.michael.websocket.service.WebsocketService;
import tw.com.eeit162.meepleMasters.michael.websocket.util.WebsocketUtil;

@Controller
public class GameRoomController {
	
	//創建房間
	@GetMapping("/game/createGameTable/{gameName}/{memberEmail}")
	public String createGame(@PathVariable("gameName") String gameName, @PathVariable("memberEmail") String memberEmail,Model model,HttpSession session) {
		if(session.getAttribute("tableCode") != null) {
			String tableCode = session.getAttribute("tableCode").toString();
			return "redirect:/game/joinGame/"+tableCode+"/"+memberEmail+"?hasCreatedRoom=true";
		}
		Member host = DataInterface.getMemberByEmail(memberEmail);
		Product product = DataInterface.getProductByProductName(gameName);
		
		//若房主以創建房間，就告訴他以創建，並跳到他的房間頁面
		//toDo
		
		List<Integer> degreeScoreList = new ArrayList<>();
		degreeScoreList.add(DataInterface.getGameDegree(host.getMemberId(), product.getProductId()).getScore());
		
		Game game = null;
		//每新增一個遊戲這邊要加一個判斷
		if("Bridge".equals(gameName)) {
			game = new Bridge();
		}
		if("Gomoku".equals(gameName)) {
			game = new Gomoku();
		}
		
		game.setMaxNumOfPlayer(product.getProductMaxPlayer());
		game.setMinNumOfPlayer(product.getProductMinPlayer());
		game.addHost(host);
		game.setGameName(gameName);
		game.setGameStatus(0);

		model.addAttribute("game", product);
		model.addAttribute("playerList", game.getPlayers());
		model.addAttribute("gameStatus", game.getGameStatus());
		model.addAttribute("ishost", true);
		model.addAttribute("degreeScoreList", degreeScoreList);
		model.addAttribute("finalNumOfPlayer", 0);

		return "/michael/gameRoom";
	}
	
	//確認遊玩人數
	@GetMapping("/game/finalNumOfPlayer/{gameName}/{memberEmail}/{numOfPlayers}")
	public String finalNumOfPlayer(@PathVariable("gameName") String gameName, @PathVariable("memberEmail") String memberEmail,@PathVariable("numOfPlayers") Integer numOfPlayers) {
		Member host = DataInterface.getMemberByEmail(memberEmail);
		Product product = DataInterface.getProductByProductName(gameName);
		Integer productId = product.getProductId();

		
		
		//若房主以創建房間且未消滅，就不行創建新房間
		Set<String> keySet = GameRoomUtil.getExistGameRoom().keySet();
		for(String key : keySet) {
			Game game = GameRoomUtil.getExistGameRoom().get(key);
			if(memberEmail.equals(game.getHostEmail())) {
				game.setFinalNumOfPlayer(numOfPlayers);
				System.out.println("重複創房間時的status"+game.getGameStatus());
				return "redirect:/game/joinGame/"+key+"/"+memberEmail;
			}
		}
		Game game = null;
		boolean isExistHost = false;
		for(Game game1 : GameRoomUtil.getExistGameRoom().values()) {
			if(game1.getHostEmail().equals(memberEmail)) {
				game = game1;
				isExistHost = true;
			}
		}
		if(!isExistHost) {
			if("Bridge".equals(gameName)) {
				game = new Bridge();
			}
			if("Gomoku".equals(gameName)) {
				game = new Gomoku();
			}
		}
		
		//每新增一個遊戲這邊要加一個判斷
		game.setMaxNumOfPlayer(product.getProductMaxPlayer());
		game.setMinNumOfPlayer(product.getProductMinPlayer());
		game.addHost(host);
		game.setGameName(gameName);
		game.setFinalNumOfPlayer(numOfPlayers);
		game.setGameStatus(1);
		
		
		String tableCode = "";
		String date = DateTimeConverter.toString(new Date(), "yyyyMMdd");
		String gameCode = date;
		if(productId<10) {
			gameCode += "00"+productId;
		}else if(productId<100) {
			gameCode += "0"+productId;
		}else {
			gameCode += ""+productId;
		}
		//存到紀錄遊戲的Map裡面
		tableCode = GameRoomUtil.createNewRoom(gameCode, game);
//		degreeScoreList.clear();
//		for(Member player : game.getPlayers()) {
//			degreeScoreList.add(DataInterface.getGameDegree(player.getMemberId(), product.getProductId()).getScore());
//		}
//		
//		model.addAttribute("game", product);
//		model.addAttribute("tableCode", tableCode);
//		model.addAttribute("playerList", game.getPlayers());
//		model.addAttribute("gameStatus", game.getGameStatus());
//		model.addAttribute("ishost", true);
//		model.addAttribute("degreeScoreList", degreeScoreList);
//		model.addAttribute("finalNumOfPlayer", 0);
		
	
//		return "/michael/gameRoom";
		return "redirect:/game/joinGame/"+tableCode+"/"+memberEmail;
	}
	
	//開放房間
	@GetMapping("/game/openRoom/{tableCode}")
	@ResponseBody
	public void openRoom(@PathVariable("tableCode") String tableCode){
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		game.setGameStatus(2);
		GameRoomUtil.openingRoom(tableCode, game);
		//通知所有在線玩家，若剛好在Lobby頁面，且此遊戲有被打開，就顯示此遊戲
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "gameIsOpening"); 
		jsonObject.put("gameName", game.getGameName());
		for(WebsocketService service : WebsocketUtil.getOnlineClient().values()) {
			String memberEmail = service.getUserEmail();
			System.out.println(memberEmail);
			WebsocketUtil.sendMessageByUserEmail(memberEmail, jsonObject.toString());
		}
		System.out.println("目前開放的桌數有幾桌"+GameRoomUtil.getOpenedGameRoom().size());
	}
	
	//房主已加入map後的離開遊戲
	@GetMapping("/game/leaveGame/{tableCode}")
	public String leaveGame(@PathVariable("tableCode") String tableCode,HttpSession session) {
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		session.removeAttribute("tableCode");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "hostCloseGame");
		//通知所有其他玩家，讓他們回到lobby畫面
		for(int i = 1;i<game.getPlayers().size();i++) {
			Member member = game.getPlayers().get(i);
			WebsocketUtil.sendMessageByUserEmail(member.getMemberEmail(), jsonObject.toString());
		}
		Integer status = game.getGameStatus();
		//請見Game狀態分別代表意思
		if(status == 2) {
			GameRoomUtil.removeOpenedRoom(tableCode);
		}
		GameRoomUtil.removeGameByTableCode(tableCode);
		return "redirect:/game/playGameLobby";
	}
	
	//玩家離開房間
	@GetMapping("/game/playerLeaveGame/{tableCode}/{memberEmail}")
	public String playerLeaveGame(@PathVariable("tableCode") String tableCode,@PathVariable("memberEmail") String memberEmail,HttpSession session) {
		//如果離開房間的玩家是本人，才清session
		Member myself = (Member)session.getAttribute("member");
		if(memberEmail.equals(myself.getMemberEmail())) {
			session.removeAttribute("tableCode");
		}
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		game.removePlayer(memberEmail);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "someoneLeaveGame");
		jsonObject.put("playerName", DataInterface.getMemberByEmail(memberEmail).getMemberName());
		for(Member member :game.getPlayers()) {
			WebsocketUtil.sendMessageByUserEmail(member.getMemberEmail(), jsonObject.toString());
		}
		return "redirect:/game/playGameLobby";
	}
	
	//房主離開遊戲，或玩家被房主踢掉時，或遊戲結束時，其他玩家用ajax來消滅session
	@GetMapping("/game/removeSessionTableCode")
	@ResponseBody
	public void removeSessionTableCode(HttpSession session) {
		session.removeAttribute("tableCode");
	}
	
	
	//玩家加入房間
	@GetMapping("/game/joinGame/{tableCode}/{memberEmail}")
	public String joinGame(@PathVariable("tableCode") String tableCode, @PathVariable("memberEmail") String memberEmail,Model model,HttpSession session,@RequestParam(value = "hasCreatedRoom",required = false) boolean hasCreatedRoom) {
		//-----------------------------------------------------------------
		model.addAttribute("hasJoinedRoom", "false");
		if(session.getAttribute("tableCode") != null) {
			if(!session.getAttribute("tableCode").equals(tableCode)) {
				model.addAttribute("hasJoinedRoom", "true");
			}
		}
		//-----------------------------------------------------------------
		model.addAttribute("hasCreatedRoom", "false");
		if(hasCreatedRoom) {
			model.addAttribute("hasCreatedRoom", "true");
		}
		//-----------------------------------------------------------------
		//如果他已經有創建過房間，或已加入房間，就直接回傳所在遊戲的頁面
		if("true".equals(model.getAttribute("hasJoinedRoom")) || hasCreatedRoom) {
			Game game = GameRoomUtil.getGameByTableCode(session.getAttribute("tableCode").toString());
			if(game ==null) {
				session.removeAttribute("tableCode");
				return "redirect:/game/playGameLobby";
			}
			if(game.getGameStatus() == 3) {
				return "redirect:/game/enterGameView/"+game.getGameName()+"/"+tableCode+"/"+memberEmail;
			}
			Product product = DataInterface.getProductByProductName(game.getGameName());
			List<Integer> degreeScoreList = new ArrayList<>();
			for(Member player : game.getPlayers()) {
				degreeScoreList.add(DataInterface.getGameDegree(player.getMemberId(), product.getProductId()).getScore());
			}
			model.addAttribute("game", product);
			model.addAttribute("tableCode", tableCode);
			model.addAttribute("playerList", game.getPlayers());
			if(memberEmail.equals(game.getHostEmail())) {
				model.addAttribute("ishost", true);
			}else {
				model.addAttribute("ishost", false);
			}
			model.addAttribute("degreeScoreList", degreeScoreList);
			model.addAttribute("finalNumOfPlayer", game.getFinalNumOfPlayer());
			model.addAttribute("gameStatus", game.getGameStatus());
			return "/michael/gameRoom";
		}
		//-----------------------------------------------------------------
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		session.setAttribute("tableCode", tableCode);
		System.out.println("玩家: "+memberEmail+" 有進來建立table的session"+session.getAttribute("tableCode"));
		if(game ==null) {
			session.removeAttribute("tableCode");
			return "redirect:/game/playGameLobby";
		}
		
		if(game.getGameStatus() == 3) {
			return "redirect:/game/enterGameView/"+game.getGameName()+"/"+tableCode+"/"+memberEmail;
		}
		boolean isInTheRoom = false;
		for(Member m:game.getPlayers()) {
			if(m.getMemberEmail().equals(memberEmail)) {
				isInTheRoom = true;
			}
		}
		if(!isInTheRoom && game.getPlayers().size() == game.getFinalNumOfPlayer()) {
			return "redirect:/game/playGameLobby";
		}
		if(!isInTheRoom && game.getPlayers().size() < game.getFinalNumOfPlayer()) {
			Member member = DataInterface.getMemberByEmail(memberEmail);
			
			game.addPlayer(member);
		}
		
		Product product = DataInterface.getProductByProductName(game.getGameName());
		List<Integer> degreeScoreList = new ArrayList<>();
		//告訴其他人我加入房間了
		//需要資料，是不是房主，玩家名，玩家熟練度，玩家id
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "someoneJoinGame");
		
		for(Member player : game.getPlayers()) {
			if(player.getMemberEmail().equals(memberEmail)) {
				jsonObject.put("playerName",player.getMemberName());
				jsonObject.put("playerEmaiil",player.getMemberEmail());
				jsonObject.put("playerId",player.getMemberId());
				jsonObject.put("playerDegree", DataInterface.getGameDegree(player.getMemberId(), product.getProductId()).getScore());
			}
			degreeScoreList.add(DataInterface.getGameDegree(player.getMemberId(), product.getProductId()).getScore());
		}
		
		
		model.addAttribute("game", product);
		model.addAttribute("tableCode", tableCode);
		model.addAttribute("playerList", game.getPlayers());
		System.out.println("還有幾個人在房間裡"+game.getPlayers());
		if(memberEmail.equals(game.getHostEmail())) {
			model.addAttribute("ishost", true);
		}else {
			model.addAttribute("ishost", false);
		}
		model.addAttribute("degreeScoreList", degreeScoreList);
		model.addAttribute("finalNumOfPlayer", game.getFinalNumOfPlayer());
		model.addAttribute("gameStatus", game.getGameStatus());
		
		
		
		for(Member member:game.getPlayers()) {
			if(!member.getMemberEmail().equals(memberEmail)) {
				if(member.getMemberEmail().equals(game.getHostEmail())) {
					jsonObject.put("ishost", true);
				}else {
					jsonObject.put("ishost",false);
				}
				WebsocketUtil.sendMessageByUserEmail(member.getMemberEmail(), jsonObject.toString());
			}
		}
		return "/michael/gameRoom";
	}
	
	//房主踢出玩家時
	@GetMapping("/game/kickPlayerOut/{tableCode}/{memberEmail}")
	public String kickPlayerOut(@PathVariable("tableCode") String tableCode,@PathVariable("memberEmail") String memberEmail) {
		//ws告訴他整理畫面
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "youHaveBeenKickOutOfTheRoom");
		WebsocketUtil.sendMessageByUserEmail(memberEmail,jsonObject.toString());

		return "redirect:/game/playerLeaveGame/"+tableCode+"/"+memberEmail;
	}
	
	//在大廳顯示已開放的房間
	@GetMapping("/game/showExistGameByGameName/{gameName}")
	@ResponseBody
	public String showExistGameByGameName(@PathVariable("gameName") String gameName) {
		Product product = DataInterface.getProductByProductName(gameName);
		System.out.println(GameRoomUtil.getOpenedGameRoom().size());
		JSONObject jsonObject = new JSONObject();
		Map<String,Game> map = new HashMap<>();
		Map<String, Game> existGameRoom = GameRoomUtil.getOpenedGameRoom();
		Set<String> keySet = existGameRoom.keySet();
		
		for(String key:keySet) {
			if("Bridge".equals(gameName)) {
				if(existGameRoom.get(key) instanceof Bridge) {
					map.put(key, existGameRoom.get(key));
				}
			}
			if("Gomoku".equals(gameName)) {
				if(existGameRoom.get(key) instanceof Gomoku) {
					map.put(key, existGameRoom.get(key));
				}
			}
			//增加遊戲時這裡加判斷
		}
//		System.out.println(map.size());
		jsonObject.put("table", map);
		jsonObject.put("productId", product.getProductId());
		return jsonObject.toString();
	}
	
	//開始遊戲按鈕，依照遊戲名及tableCode和玩家email來跳轉到正確的遊戲畫面
	@GetMapping("/game/startGame/{gameName}/{tableCode}/{memberEmail}")
	public String startGame(@PathVariable("gameName") String gameName,@PathVariable("tableCode") String tableCode,@PathVariable("memberEmail") String memberEmail) {
		//通知房間內所有人開始遊戲
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		//關閉開放在大廳的房間
		GameRoomUtil.removeOpenedRoom(tableCode);
		//這邊不更改遊戲狀態，等起始設置好了後才更改
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "gameStart");
		jsonObject.put("gameName", gameName);
		jsonObject.put("tableCode", tableCode);
		for(int i = 1;i<game.getPlayers().size();i++) {
			WebsocketUtil.sendMessageByUserEmail(game.getPlayers().get(i).getMemberEmail(), jsonObject.toString());
		}
		//呼叫進入遊戲畫面的controller
		return "redirect:/game/enterGameView/"+gameName+"/"+tableCode+"/"+memberEmail;
	}
	
	//進入遊戲畫面
	@GetMapping("/game/enterGameView/{gameName}/{tableCode}/{memberEmail}")
	public String enterGameView(@PathVariable("gameName") String gameName,@PathVariable("tableCode") String tableCode,@PathVariable("memberEmail") String memberEmail) {
		//依照遊戲導向各個遊戲的controller
		if("Bridge".equals(gameName)) {
			return "redirect:/bridge/enterGameView/"+tableCode+"/"+memberEmail;
		}
		if("Gomoku".equals(gameName)) {
			return "redirect:/gomoku/enterGameView/"+tableCode+"/"+memberEmail;
		}
		return null;
	}
	
	
	
}
