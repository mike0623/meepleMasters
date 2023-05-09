package tw.com.eeit162.meepleMasters.michael.game.bridge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

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
	
	private Integer team1WonTricks;

	private Integer team2WonTricks;
	
	private Set<Integer> discardSet;
	
	private Integer trump; //王牌 0-3分別為黑桃，紅心，方塊，梅花，4為無王
	
	private Integer forTwoPlayersCard;
	
	private Integer phase; //1:等待玩家一出牌，2:等待玩家二出牌，3:等待玩家三出牌，4:等待玩家四出牌，5:每人都出玩牌時，停留3秒讓大家看牌
				   		   //11:等待玩家一喊王 21:等待玩家二喊王 31:等待玩家三喊王 41:等待玩家四喊王 
	
	private Integer playerNTurn; //誰的回合
	
	private Integer playerNBid; //誰叫的王牌，pass不算
	
	private BridgePlayer perTurnWinner;
	
	private ArrayList<BridgePlayer> playerSeat = new ArrayList<>();
	
	
	
	
	
	

	public Bridge() {
	}
	
	
	public void initGameSet(Game game) {
		//牌庫裝滿
		resetDesk();
		//建立玩家
		createPlayers(game.getFinalNumOfPlayer());
		//將玩家基本資料寫入BridgePlayer物件裡
		writePlayersInfo(game.getPlayers());
		//隨機分隊
		decidePlayerSeatAndTeam(game.getFinalNumOfPlayer());

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
			}
			if(temp.get(i) == 2) {
				playerSeat.add(player2);
				player2.setTeam(2);
			}
			if(temp.get(i) == 3) {
				playerSeat.add(player3);
				player3.setTeam(3);
			}
			if(temp.get(i) == 4) {
				playerSeat.add(player4);
				player4.setTeam(4);
			}
			temp.remove(i);
		}
		System.out.println("玩家座位"+playerSeat);
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


	public Integer getPlayerNTurn() {
		return playerNTurn;
	}


	public void setPlayerNTurn(Integer playerNTurn) {
		this.playerNTurn = playerNTurn;
	}


	public Integer getPlayerNBid() {
		return playerNBid;
	}


	public void setPlayerNBid(Integer playerNBid) {
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
	
	
	
	
	
	

	
	
}
