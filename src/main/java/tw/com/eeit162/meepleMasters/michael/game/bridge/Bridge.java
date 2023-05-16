package tw.com.eeit162.meepleMasters.michael.game.bridge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.michael.game.Game;
import tw.com.eeit162.meepleMasters.michael.game.degree.model.GameDegree;
import tw.com.eeit162.meepleMasters.michael.util.DataInterface;

public class Bridge extends Game{
	
	private Set<Integer> deskSet = new HashSet<>();
	private List<Integer> deskList = new ArrayList<>();
	
	private BridgePlayer player1 = null;
	private BridgePlayer player2 = null;
	private BridgePlayer player3 = null;
	private BridgePlayer player4 = null;
	
	private Integer team1WinRequirement;

	private Integer team2WinRequirement;
	
	private Integer team1WonTricks = 0;

	private Integer team2WonTricks = 0;
	
	private Set<Integer> discardSet; //沒用到
	
	private Integer forTwoPlayersCard;
	
	private Integer phase; //1:等待玩家一出牌，2:等待玩家二出牌，3:等待玩家三出牌，4:等待玩家四出牌
				   		   //11:等待玩家一喊王 21:等待玩家二喊王 31:等待玩家三喊王 41:等待玩家四喊王 
	
	private BridgePlayer playerNTurn; //誰的回合
	
	private BridgePlayer playerNBid = null; //誰叫的王牌，pass不算

	private Integer trump = 4; //王牌 0-3分別為黑桃，紅心，方塊，梅花，4為無王
	
	private Integer trumpLevel = 0; //喊王喊到數字幾
	
	private Integer countBidPass = 0;//計算連續喊了幾次pass

	private BridgePlayer perTurnWinner;
	
	private ArrayList<BridgePlayer> playerSeat = new ArrayList<>();
	
//	private ArrayList<Integer> simplePlayOrder = new ArrayList<>();
	
	private Integer usedCardPerTurn = 0;
	
	private Integer perTurnSuit = null; //每輪的花色
	
	private boolean isEndOfTheGame = false;
	
	private String winTeam; //紅隊或藍隊
	
	
	
	

	public Bridge() {
	}
	
	
	public void initGameSet(Game game) {
		//牌庫裝滿
		resetDesk();
		//建立玩家
		createPlayers(game.getFinalNumOfPlayer());
		//將玩家基本資料寫入BridgePlayer物件裡
		writePlayersInfo(game.getPlayers());
		//隨機分隊和遊戲順序
		decidePlayerSeatAndTeam(game.getFinalNumOfPlayer());
		//設置遊戲階段
		setPhase(playerSeat.get(0),true);
		setPlayerNTurn(playerSeat.get(0));
		//兩人遊戲時，桌面上的牌
		if(game.getFinalNumOfPlayer() == 2) {
			setTwoPlayerCard();
			countBidPass = -1;
		}
		
	}
	
	
	
	//牌庫裝滿
	public void resetDesk() {
		if(deskSet.size() != 0) {
			deskSet.clear();
		}
		if(deskList.size() != 0) {
			deskList.clear();
		}
		for(int i = 1;i<=52;i++) {
			deskSet.add(i);
			deskList.add(i);
		}
	}
	//遊戲開始時，依照人數建立player物件，並發起始手牌
	public void createPlayers(Integer numOfPlayers) {
		if(numOfPlayers >= 2) {
			player1 = new BridgePlayer(1);
			resetHandCard(player1);
			player2 = new BridgePlayer(2);
			resetHandCard(player2);
		}
		if(numOfPlayers >= 3) {
			player3 = new BridgePlayer(3);
			resetHandCard(player3);
		}
		if(numOfPlayers == 4) {
			player4 = new BridgePlayer(4);
			resetHandCard(player4);
		}
	}
	
	//遊戲開始時，將玩家基本資料寫入BridgePlayer物件裡
	public void writePlayersInfo(List<Member> players) {
		for(int i = 0;i<players.size();i++) {
			if(i == 0) {
				player1.setEmail(players.get(i).getMemberEmail());
				player1.setName(players.get(i).getMemberName());
				Product product = DataInterface.getProductByProductName(getGameName());
				GameDegree gameDegree = DataInterface.getGameDegree(players.get(i).getMemberId(), product.getProductId());
				player1.setBridgeDegree(gameDegree.getScore());
			}
			if(i == 1) {
				player2.setEmail(players.get(i).getMemberEmail());
				player2.setName(players.get(i).getMemberName());
				Product product = DataInterface.getProductByProductName(getGameName());
				GameDegree gameDegree = DataInterface.getGameDegree(players.get(i).getMemberId(), product.getProductId());
				player2.setBridgeDegree(gameDegree.getScore());
			}
			if(i == 2) {
				player3.setEmail(players.get(i).getMemberEmail());
				player3.setName(players.get(i).getMemberName());
				Product product = DataInterface.getProductByProductName(getGameName());
				GameDegree gameDegree = DataInterface.getGameDegree(players.get(i).getMemberId(), product.getProductId());
				player3.setBridgeDegree(gameDegree.getScore());
			}
			if(i == 3) {
				player4.setEmail(players.get(i).getMemberEmail());
				player4.setName(players.get(i).getMemberName());
				Product product = DataInterface.getProductByProductName(getGameName());
				GameDegree gameDegree = DataInterface.getGameDegree(players.get(i).getMemberId(), product.getProductId());
				player4.setBridgeDegree(gameDegree.getScore());
			}
		}
	}
	
	//遊戲開始時，隨機決定玩家位置與隊伍
	public void decidePlayerSeatAndTeam(Integer numOfPlayers) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i = 1;i<=numOfPlayers;i++) {
			temp.add(i);
		}
		while(temp.size() >0) {
			int i = (int)Math.floor((Math.random()*temp.size()));
			if(temp.get(i) == 1) {
				playerSeat.add(player1);
				player1.setTeam(1);
//				simplePlayOrder.add(1);
			}
			if(temp.get(i) == 2) {
				playerSeat.add(player2);
				player2.setTeam(2);
//				simplePlayOrder.add(2);
			}
			if(temp.get(i) == 3) {
				playerSeat.add(player3);
				player3.setTeam(1);
//				simplePlayOrder.add(3);
			}
			if(temp.get(i) == 4) {
				playerSeat.add(player4);
				player4.setTeam(2);
//				simplePlayOrder.add(4);
			}
			temp.remove(i);
		}
		System.out.println("玩家座位"+playerSeat);
		//設定起始的playerNBid
		setPlayerNBid(playerSeat.get(playerSeat.size()-1));
	}
	
	//發起始手牌
	public void resetHandCard(BridgePlayer player) {
		if(player.getHandCardSet().size() != 0) {
			player.getHandCardSet().clear();
		}
		if(player.getHandCardList().size() != 0) {
			player.getHandCardList().clear();
		}
		for(int j =0;j<13;j++) {
			int i = (int)Math.floor((Math.random()*deskList.size()));
			Integer card = deskList.get(i);
			if(deskSet.remove(card)) {
				deskList.remove(i);
			}
			player.getHandCardSet().add(card);
		}
		//等set拿完13張並排序好了後，再放到list裡面
		for(Integer card1:player.getHandCardSet()) {
			player.getHandCardList().add(card1);
		}
	}
	
	//兩人遊戲時中間的卡片
	public void setTwoPlayerCard() {
		int i = (int)Math.floor((Math.random()*deskList.size()));
		Integer card = deskList.get(i);
		if(deskSet.remove(card)) {
			deskList.remove(i);
			forTwoPlayersCard = card;
		}
	}
	
	//設置遊戲階段
	//Overloading
	public void setPhase(BridgePlayer player,boolean isBiddingPhase) {
		if(player == null) {
			phase = 5;
		}
		if(isBiddingPhase) {
			phase = player.getPlayerNumber()*10 + 1;
			return;
		}
		if(!isBiddingPhase) {
			phase = player.getPlayerNumber();
			return;
		}
	}
	
	//找出玩家手牌特定花色剩幾張
	public Integer getNumOfSpecific(BridgePlayer player,Integer suit) {
		Integer count = 0;
		for(Integer card:player.getHandCardList()) {
			if(card == 1) {
				if(suit == 0) {
					count++;
					continue;
				}
			}
			if(Math.floor((card-1)/13) == suit) {
				count++;
			}
		}
		return count;
	}
	
	//用email找到player
	public BridgePlayer findBridgePlayerByEmail(String email) {
		for(BridgePlayer player : playerSeat) {
			if(player.getEmail().equals(email)) {
				return player;
			}
		}
		return null;
	}
	
	//用牌找到對應花色
	public Integer getSuitByCard(Integer card) {
		Integer testSuits = Math.floorDiv(card, 13);
		Integer testNumber =  card % 13;
		if(testNumber == 0) {
			testSuits -= 1;
		}
		return testSuits;
	}
	
	//用牌找到對應花色及數字
	public String getStringByCard(Integer card) {
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
				suits = "黑桃";
			}
			if(testSuits == 1) {
				suits = "紅心";
			}
			if(testSuits == 2) {
				suits = "方塊";
			}
			if(testSuits == 3) {
				suits = "梅花";
			}
		}
		return suits+number;
	}
	
	//用王牌數字找到對應文字
	public String transTrumpFromStringAndInteger(Integer integer) {
		if(integer == 0) {
			return "黑桃";
		}
		if(integer == 1) {
			return "紅心";
		}
		if(integer == 2) {
			return "方塊";
		}
		if(integer == 3) {
			return "梅花";
		}
		if(integer == 4) {
			return "無王";
		}
		return null;
	}
	
	//取得玩家位置，0開始
	public Integer getPlayerSeatIndex(BridgePlayer player) {
		Integer index = 0;
		for(int i = 0;i<playerSeat.size();i++) {
			if(playerSeat.get(i) == player) {
				index = i;
			}
		}
		return index;
	}
	
	//兩人遊戲時，phase1快轉到牌庫剩一張
	public void forTwoPlayersfastForward() {
		Integer deskCard = deskList.get(0);
		deskList.clear();
		deskSet.clear();
		deskList.add(deskCard);
		deskSet.add(deskCard);
	}
	
	//按下喊王按鈕時的方法，回傳是否還是喊王階段
	public boolean bidding(BridgePlayer player,String suit,Integer trumpLevel) {
		if("跳過".equals(suit)) {
			countBidPass++;
			if(countBidPass == playerSeat.size()-1) {
				return false;
			}
			return true;
		}
		playerNBid = player;
		if("黑桃".equals(suit)) {
			trump = 0;
		}
		if("紅心".equals(suit)) {
			trump = 1;
		}
		if("方塊".equals(suit)) {
			trump = 2;
		}
		if("梅花".equals(suit)) {
			trump = 3;
		}
		if("無王".equals(suit)) {
			trump = 4;
		}
		this.trumpLevel = trumpLevel;
		countBidPass = 0;
		return true;
	}
	
	//換下一位的回合
	public BridgePlayer nextTurn(BridgePlayer oldPlayer,boolean isBiddingPhase) {
		Integer oldIndex = 0;
		for(int i = 0;i<playerSeat.size();i++) {
			if(oldPlayer.getPlayerNumber() == playerSeat.get(i).getPlayerNumber()) {
				oldIndex = i;
			}
		}
		if(isBiddingPhase) {
			Integer newIndex = 0;
			if(oldIndex == 0) {
				newIndex = playerSeat.size()-1;
			}else {
				newIndex = oldIndex -1;
			}
			playerNTurn = playerSeat.get(newIndex);
			return playerNTurn;
		}
		if(!isBiddingPhase) {
			Integer newIndex = 0;
			if(oldIndex == playerSeat.size()-1) {
				newIndex = 0;
			}else {
				newIndex = oldIndex +1;
			}
			playerNTurn = playerSeat.get(newIndex);
			return playerNTurn;
		}
		return null;
	}
	
	//喊王階段結束時，計算兩隊勝利所需墩數
	public void countWinRequirement() {
		if(playerNBid.getTeam() == 1) {
			team1WinRequirement = 6+trumpLevel;
			team2WinRequirement = 14-team1WinRequirement;
		}
		if(playerNBid.getTeam() == 2) {
			team2WinRequirement = 6+trumpLevel;
			team1WinRequirement = 14-team2WinRequirement;
		}
	}
	
	//出牌時，紀錄自己出的牌  如果所有玩家都出牌了，就進入階段五，
	public void useCard(BridgePlayer player,Integer card) {
		player.useCard(card);
		usedCardPerTurn++;
	}
	
	//判斷出牌階段是否回合結束
	public boolean isEndOfTheTurn() {
		if(usedCardPerTurn == playerSeat.size()) {
			return true;
		}
		return false;
	}
	
	//決定本輪贏家
	//overloading
	public void setPerTurnWinner() {
		BridgePlayer winner = null;
		for(BridgePlayer player:playerSeat) {
			if(winner == null) {
				winner = player;
				continue;
			}
			Integer challengerCard = player.getPlayedCard();
			Integer winnerCard = winner.getPlayedCard();
			Integer challengerCardSuit = getSuitByCard(challengerCard);
			Integer winnerCardSuit = getSuitByCard(winnerCard);
			//挑戰為當，挑戰為王
			if(challengerCardSuit == perTurnSuit && challengerCardSuit == trump) {
				if(winnerCardSuit == perTurnSuit) {
					//戰鬥
					if((challengerCard+11) % 13 > (winnerCard+11) % 13) {
						winner = player;
					}
					continue;
				}else {
					winner = player;
					continue;
				}
			}
			//挑戰為當，挑戰非王
			if(challengerCardSuit == perTurnSuit && challengerCardSuit != trump) {
				if(winnerCardSuit == perTurnSuit && winnerCardSuit != trump) {
					//戰鬥
					if((challengerCard+11) % 13 > (winnerCard+11) % 13) {
						winner = player;
					}
					continue;
				}
				if(winnerCardSuit != perTurnSuit && winnerCardSuit == trump) {
					continue;
				}
				if(winnerCardSuit != perTurnSuit && winnerCardSuit != trump) {
					winner = player;
					continue;
				}
			}
			//挑戰非當，挑戰為王
			if(challengerCardSuit != perTurnSuit && challengerCardSuit == trump) {
				if(winnerCardSuit != perTurnSuit && winnerCardSuit == trump) {
					//戰鬥
					if((challengerCard+11) % 13 > (winnerCard+11) % 13) {
						winner = player;
					}
					continue;
				}
				winner = player;
				continue;
			}
			//挑戰非當，挑戰非王
			if(challengerCardSuit != perTurnSuit && challengerCardSuit == trump) {
				if(winnerCardSuit != perTurnSuit && winnerCardSuit != trump) {
					//戰鬥
					if((challengerCard+11) % 13 > (winnerCard+11) % 13) {
						winner = player;
					}
					continue;
				}
				continue;
			}
		}
		perTurnWinner = winner;
	}
	
	//兩人玩時第一階段，回傳是否還是第一階段
	public boolean twoPlayerPhaseOne() {
		System.out.println("誰是贏家"+perTurnWinner.getName());
		perTurnWinner.getHandCardSet().add(forTwoPlayersCard);
		perTurnWinner.getHandCardList().clear();
		for(Integer card:perTurnWinner.getHandCardSet()) {
			perTurnWinner.getHandCardList().add(card);
		}
		int i = (int)Math.floor((Math.random()*deskList.size()));
		Integer deskCard = deskList.get(i);
		for(BridgePlayer player:playerSeat) {
			if(player != perTurnWinner) {
				player.getHandCardSet().add(deskCard);
				player.getHandCardList().clear();
				for(Integer card:player.getHandCardSet()) {
					player.getHandCardList().add(card);
				}
			}
		}
		if(deskSet.remove(deskCard)) {
			deskList.remove(deskCard);
		}
		//補充新卡
		if(deskSet.size() != 0) {
			int j = (int)Math.floor((Math.random()*deskList.size()));
			Integer newForTwoPlayersCard = deskList.get(j);
			forTwoPlayersCard = newForTwoPlayersCard;
			System.out.println("新的卡"+forTwoPlayersCard);
			if(deskSet.remove(newForTwoPlayersCard)) {
				deskList.remove(newForTwoPlayersCard);
			}
			return true;
		}
		return false;
	}
	
	//清空所有玩家已出卡片
	public void resetPlayersUsedCard() {
		for(BridgePlayer player:playerSeat) {
			player.setPlayedCard(null);
		}
	}
	
	//一輪結束時，計分
	public void plusScoreWhenEndOfTheTurn() {
		perTurnWinner.setWonTricks(perTurnWinner.getWonTricks()+1);
		if(perTurnWinner.getTeam() == 1) {
			team1WonTricks++;
		}
		if(perTurnWinner.getTeam() == 2) {
			team2WonTricks++;
		}
	}
	
	//哪隊勝利
	public void makeWinTeam() {
		if(team1WonTricks > team1WinRequirement) {
			setWinTeam("紅隊");
		}
		if(team2WonTricks > team2WinRequirement) {
			setWinTeam("藍隊");
		}
	}
	
	//製作遊戲結束時的資訊，11項資訊
	public JSONObject InfoWhenEndOfGame() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "bridgeGame");
		jsonObject.put("gameAction", "infoWhenEndOfGame");
		jsonObject.put("isEndOfTheGame", getIsEndOfTheGame());
		jsonObject.put("isEndOfTheTurn", true);
		Integer winTeamInt = 0;
		if("紅隊".equals(winTeam)) {
			winTeamInt = 1;
		}
		if("藍隊".equals(winTeam)) {
			winTeamInt = 2;
		}
		List<BridgePlayer> winTeamPlayers = new ArrayList<>();
		List<BridgePlayer> loseTeamPlayers = new ArrayList<>();
		for(BridgePlayer player:playerSeat) {
			if(player.getTeam() == winTeamInt) {
				winTeamPlayers.add(player);
			}else {
				loseTeamPlayers.add(player);
			}
		}
		jsonObject.put("winTeamPlayers", winTeamPlayers);
		jsonObject.put("loseTeamPlayers", loseTeamPlayers);
		if(winTeamInt == 1) {
			jsonObject.put("winTeamRequirement", team1WinRequirement);
			jsonObject.put("loseTeamRequirement", team2WinRequirement);
			jsonObject.put("winTeamWonTricks", team1WonTricks);
			jsonObject.put("loseTeamWonTricks", team2WonTricks);
		}
		if(winTeamInt == 2) {
			jsonObject.put("winTeamRequirement", team2WinRequirement);
			jsonObject.put("loseTeamRequirement", team1WinRequirement);
			jsonObject.put("winTeamWonTricks", team2WonTricks);
			jsonObject.put("loseTeamWonTricks", team1WonTricks);
		}
		jsonObject.put("playerNBid", playerNBid.getName());
		if(trump == 0) {
			jsonObject.put("trump", "黑桃");
		}
		if(trump == 1) {
			jsonObject.put("trump", "紅心");
		}
		if(trump == 2) {
			jsonObject.put("trump", "方塊");
		}
		if(trump == 3) {
			jsonObject.put("trump", "梅花");
		}
		if(trump == 4) {
			jsonObject.put("trump", "無王");
		}
		jsonObject.put("trumpLevel", trumpLevel);
		jsonObject.put("winTeam", winTeam);
		jsonObject.put("playerSeat", playerSeat);
		jsonObject.put("isThisTurnTwoPlayerPhaseOne", false);
		
		
		return jsonObject;
	}

	
	
	
	
	
	
	
	//-----------------------------------------------
	//getter && setter
	public Set<Integer> getDeskSet() {
		return deskSet;
	}


	public void setDeskSet(Set<Integer> deskSet) {
		this.deskSet = deskSet;
	}


	public List<Integer> getDeskList() {
		return deskList;
	}


	public void setDeskList(List<Integer> deskList) {
		this.deskList = deskList;
	}


	public BridgePlayer getPlayer1() {
		return player1;
	}


	public void setPlayer1(BridgePlayer player1) {
		this.player1 = player1;
	}


	public BridgePlayer getPlayer2() {
		return player2;
	}


	public void setPlayer2(BridgePlayer player2) {
		this.player2 = player2;
	}


	public BridgePlayer getPlayer3() {
		return player3;
	}


	public void setPlayer3(BridgePlayer player3) {
		this.player3 = player3;
	}


	public BridgePlayer getPlayer4() {
		return player4;
	}


	public void setPlayer4(BridgePlayer player4) {
		this.player4 = player4;
	}



	public Integer getTeam1WinRequirement() {
		return team1WinRequirement;
	}


	public void setTeam1WinRequirement(Integer team1WinRequirement) {
		this.team1WinRequirement = team1WinRequirement;
	}


	public Integer getTeam2WinRequirement() {
		return team2WinRequirement;
	}


	public void setTeam2WinRequirement(Integer team2WinRequirement) {
		this.team2WinRequirement = team2WinRequirement;
	}


	public Integer getTeam1WonTricks() {
		return team1WonTricks;
	}


	public void setTeam1WonTricks(Integer team1WonTricks) {
		this.team1WonTricks = team1WonTricks;
	}


	public Integer getTeam2WonTricks() {
		return team2WonTricks;
	}


	public void setTeam2WonTricks(Integer team2WonTricks) {
		this.team2WonTricks = team2WonTricks;
	}


	public Set<Integer> getDiscardSet() {
		return discardSet;
	}


	public void setDiscardSet(Set<Integer> discardSet) {
		this.discardSet = discardSet;
	}


	public Integer getTrump() {
		return trump;
	}


	public void setTrump(Integer trump) {
		this.trump = trump;
	}


	public Integer getForTwoPlayersCard() {
		return forTwoPlayersCard;
	}


	public void setForTwoPlayersCard(Integer forTwoPlayersCard) {
		this.forTwoPlayersCard = forTwoPlayersCard;
	}


	public Integer getPhase() {
		return phase;
	}


	public void setPhase(Integer phase) {
		this.phase = phase;
	}


	public BridgePlayer getPlayerNTurn() {
		return playerNTurn;
	}


	public void setPlayerNTurn(BridgePlayer playerNTurn) {
		this.playerNTurn = playerNTurn;
	}


	public BridgePlayer getPlayerNBid() {
		return playerNBid;
	}


	public void setPlayerNBid(BridgePlayer playerNBid) {
		this.playerNBid = playerNBid;
	}


	public BridgePlayer getPerTurnWinner() {
		return perTurnWinner;
	}


	public void setPerTurnWinner(BridgePlayer perTurnWinner) {
		this.perTurnWinner = perTurnWinner;
	}


	public ArrayList<BridgePlayer> getPlayerSeat() {
		return playerSeat;
	}


	public void setPlayerSeat(ArrayList<BridgePlayer> playerSeat) {
		this.playerSeat = playerSeat;
	}


	public Integer getTrumpLevel() {
		return trumpLevel;
	}


	public void setTrumpLevel(Integer trumpLevel) {
		this.trumpLevel = trumpLevel;
	}


//	public ArrayList<Integer> getSimplePlayOrder() {
//		return simplePlayOrder;
//	}
//
//
//	public void setSimplePlayOrder(ArrayList<Integer> simplePlayOrder) {
//		this.simplePlayOrder = simplePlayOrder;
//	}


	public Integer getCountBidPass() {
		return countBidPass;
	}


	public void setCountBidPass(Integer countBidPass) {
		this.countBidPass = countBidPass;
	}


	public Integer getUsedCardPerTurn() {
		return usedCardPerTurn;
	}


	public void setUsedCardPerTurn(Integer usedCardPerTurn) {
		this.usedCardPerTurn = usedCardPerTurn;
	}


	public Integer getPerTurnSuit() {
		return perTurnSuit;
	}


	public void setPerTurnSuit(Integer perTurnSuit) {
		this.perTurnSuit = perTurnSuit;
	}


	public boolean getIsEndOfTheGame() {
		return isEndOfTheGame;
	}

	
	public void setIsEndOfTheGame(boolean isEndOfTheGame) {
		this.isEndOfTheGame = isEndOfTheGame;
	}

	public String getWinTeam() {
		return winTeam;
	}


	public void setWinTeam(String winTeam) {
		this.winTeam = winTeam;
	}

	
	
	
	
	
	

	
	
}
