package tw.com.eeit162.meepleMasters.michael.game.room;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import tw.com.eeit162.meepleMasters.michael.util.DataInterface;
import tw.com.eeit162.meepleMasters.michael.util.DateTimeConverter;
import tw.com.eeit162.meepleMasters.michael.websocket.service.WebsocketService;
import tw.com.eeit162.meepleMasters.michael.websocket.util.WebsocketUtil;

@Controller
public class GameRoomController {
	
	//創建房間
	@GetMapping("/game/createGameTable/{gameName}/{memberEmail}")
	public String createGame(@PathVariable("gameName") String gameName, @PathVariable("memberEmail") String memberEmail,Model model) {
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
		
		game.setMaxNumOfPlayer(product.getProductMaxPlayer());
		game.setMinNumOfPlayer(product.getProductMinPlayer());
		game.addHost(host);
		game.setGameName(gameName);
		game.setGameStatus(0);
		String tableCode = "";

		model.addAttribute("game", product);
		model.addAttribute("tableCode", tableCode);
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
	}
	
	//房主已加入map後的離開遊戲
	@GetMapping("/game/leaveGame/{tableCode}")
	public String leaveGame(@PathVariable("tableCode") String tableCode) {
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
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
	public String playerLeaveGame(@PathVariable("tableCode") String tableCode,@PathVariable("memberEmail") String memberEmail) {
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		for(int i = 0;i<game.getPlayers().size();i++) {
			if(game.getPlayers().get(i).getMemberEmail().equals(memberEmail)) {
				game.getPlayers().remove(i);
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "someoneLeaveGame");
		for(Member member :game.getPlayers()) {
			WebsocketUtil.sendMessageByUserEmail(member.getMemberEmail(), jsonObject.toString());
		}
		return "redirect:/game/playGameLobby";
	}
	
	
	//玩家加入房間
	@GetMapping("/game/joinGame/{tableCode}/{memberEmail}")
	public String joinGame(@PathVariable("tableCode") String tableCode, @PathVariable("memberEmail") String memberEmail,Model model) {
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		if(game ==null) {
			System.out.println("存在的房間數:"+GameRoomUtil.getExistGameRoom().size());
			return "redirect:/game/playGameLobby";
		}
		boolean isInTheRoom = false;
		for(Member m:game.getPlayers()) {
			if(m.getMemberEmail().equals(memberEmail)) {
				isInTheRoom = true;
			}
		}
		if(!isInTheRoom) {
			Member member = DataInterface.getMemberByEmail(memberEmail);
			game.addPlayer(member);
		}
		
		Product product = DataInterface.getProductByProductName(game.getGameName());
		List<Integer> degreeScoreList = new ArrayList<>();
		for(Member player : game.getPlayers()) {
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
		//告訴其他人我加入房間了
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "someoneJoinGame");
		for(Member member:game.getPlayers()) {
			if(!member.getMemberEmail().equals(memberEmail)) {
				WebsocketUtil.sendMessageByUserEmail(member.getMemberEmail(), jsonObject.toString());
			}
		}
		return "/michael/gameRoom";
	}
	
	//房主踢出玩家時
	@GetMapping("/game/kickPlayerOut/{tableCode}/{memberEmail}")
	@ResponseBody
	public void kickPlayerOut(@PathVariable("tableCode") String tableCode,@PathVariable("memberEmail") String memberEmail) {
		//將玩家從map中移除
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
		game.removePlayer(memberEmail);
		//ws告訴他整理畫面
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "youHaveBeenKickOutOfTheRoom");
		WebsocketUtil.sendMessageByUserEmail(memberEmail,jsonObject.toString());
	}
	
	//在大廳顯示已開放的房間
	@GetMapping("/game/showExistGameByGameName/{gameName}")
	@ResponseBody
	public String showExistGameByGameName(@PathVariable("gameName") String gameName) {
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
			//增加遊戲時這裡加判斷
		}
//		System.out.println(map.size());
		jsonObject.put("table", map);
		return jsonObject.toString();
	}
	
	//開始遊戲按鈕，依照遊戲名及tableCode和玩家email來跳轉到正確的遊戲畫面
	@GetMapping("/game/startGame/{gameName}/{tableCode}/{memberEmail}")
	public String startGame(@PathVariable("gameName") String gameName,@PathVariable("tableCode") String tableCode,@PathVariable("memberEmail") String memberEmail) {
		//通知房間內所有人開始遊戲
		Game game = GameRoomUtil.getGameByTableCode(tableCode);
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
		return null;
	}
	
	
}
