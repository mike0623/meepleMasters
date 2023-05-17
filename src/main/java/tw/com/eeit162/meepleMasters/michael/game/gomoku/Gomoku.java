package tw.com.eeit162.meepleMasters.michael.game.gomoku;

import java.util.HashMap;
import java.util.Map;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.michael.game.Game;
import tw.com.eeit162.meepleMasters.michael.util.DataInterface;

public class Gomoku extends Game{
	//存整個棋盤的每個位置是什麼顏色，一開始是null
	private Map<String,String> chessBoard = new HashMap<>(); 
	
	private String player1Name;
	
	private String player2Name;
	
	private String player1Email;
	
	private String player1Color; // black 或 white
	
	private String player2Email;
	
	private Integer player1Degree;

	private Integer player2Degree;
	
	private String player2Color;
	
	private String playerNTurn; //誰的回合，記email
	
	private boolean endOfTheGame = false;
	
	private String winner; //記名子
	
	private String loser;
	
	
	
	
	
	public Gomoku() {
	}
	
	public void initGameSet(Game game) {
		resetChessBoard();
		setPlayerInfo(game);
		randomBeginnerer();
	}
	
	//起始棋盤所有位置顏色都是null
	public void resetChessBoard() {
	    for(int i = 1;i<=19;i++) {
			for(int j = 1;j<=19;j++) {
				String position = "x"+j+"y"+i;
				chessBoard.put(position, null);
			}
		}
	 }
	 
	 //初始化玩家資料
	 public void setPlayerInfo(Game game) {
		 Member player1 = game.getPlayers().get(0);
		 Member player2 = game.getPlayers().get(1);
		 Product product = DataInterface.getProductByProductName(game.getGameName());

		 this.player1Email = player1.getMemberEmail();
		 this.player1Name = player1.getMemberName();
		 this.player1Degree = DataInterface.getGameDegree(player1.getMemberId(), product.getProductId()).getScore();
		 
		 this.player2Email = player2.getMemberEmail();
		 this.player2Name = player2.getMemberName();
		 this.player2Degree = DataInterface.getGameDegree(player2.getMemberId(), product.getProductId()).getScore();
	 }
	 
	 //隨機一個人先開始
	 public void randomBeginnerer() {
		 int i = (int)Math.floor((Math.random()*2));
		 if(i == 0) {
			 player1Color = "black";
			 player2Color = "white";
			 this.playerNTurn = player1Email;
		 }
		 if(i == 1) {
			 player2Color = "black";
			 player1Color = "white";
			 this.playerNTurn = player2Email;
		 }
	 }
	 
	 //判斷是否獲勝
	 public boolean isWin(Integer x,Integer y) {
		 String color = chessBoard.get("x"+x+"y"+y);
		 Integer count = 1;
		 Integer i = 1;
		 //y軸---------------------------------------------------
		 while(color.equals(chessBoard.get("x"+x+"y"+(y+i)))) {
			 count++;
			 i++;
		 }
		 i = 1;
		 while(color.equals(chessBoard.get("x"+x+"y"+(y-i)))) {
			 count++;
			 i++;
		 }
		 if(count >= 5) {
			 return true;
		 }else {
			 count = 1;
			 i = 1;
		 }
		//---------------------------------------------------
		//右上左下---------------------------------------------------
		 while(color.equals(chessBoard.get("x"+(x+i)+"y"+(y+i)))) {
			 count++;
			 i++;
		 }
		 i = 1;
		 while(color.equals(chessBoard.get("x"+(x-i)+"y"+(y-i)))) {
			 count++;
			 i++;
		 }
		 if(count >= 5) {
			 return true;
		 }else {
			 count = 1;
			 i = 1;
		 }
		//---------------------------------------------------
		//x軸---------------------------------------------------
		 while(color.equals(chessBoard.get("x"+(x+i)+"y"+y))) {
			 count++;
			 i++;
		 }
		 i = 1;
		 while(color.equals(chessBoard.get("x"+(x-i)+"y"+y))) {
			 count++;
			 i++;
		 }
		 if(count >= 5) {
			 return true;
		 }else {
			 count = 1;
			 i = 1;
		 }
		//---------------------------------------------------
		//左上右下---------------------------------------------------
		 while(color.equals(chessBoard.get("x"+(x-i)+"y"+(y+i)))) {
			 count++;
			 i++;
		 }
		 i = 1;
		 while(color.equals(chessBoard.get("x"+(x+i)+"y"+(y-i)))) {
			 count++;
			 i++;
		 }
		 if(count >= 5) {
			 return true;
		 }else {
			 count = 1;
			 i = 1;
		 }
		//---------------------------------------------------
		 return false;
	 }
	 
	 //換人
	 public void changePlayerTurn(String memberEmail) {
		 if(memberEmail.equals(player1Email)) {
			 playerNTurn = player2Email;
		 }
		 if(memberEmail.equals(player2Email)) {
			 playerNTurn = player1Email;
		 }
	 }
	 
	 //取得顏色
	 public String getColorByPosition(Integer x,Integer y) {
		 return chessBoard.get("x"+x+"y"+y);
	 }
	 
	 
	 
	 //-------------------------------------
	public Map<String, String> getChessBoard() {
		return chessBoard;
	}

	public void setChessBoard(Map<String, String> chessBoard) {
		this.chessBoard = chessBoard;
	}

	public String getPlayer1Name() {
		return player1Name;
	}

	public void setPlayer1Name(String player1Name) {
		this.player1Name = player1Name;
	}

	public String getPlayer2Name() {
		return player2Name;
	}

	public void setPlayer2Name(String player2Name) {
		this.player2Name = player2Name;
	}

	public String getPlayer1Email() {
		return player1Email;
	}

	public void setPlayer1Email(String player1Email) {
		this.player1Email = player1Email;
	}

	public String getPlayer2Email() {
		return player2Email;
	}

	public void setPlayer2Email(String player2Email) {
		this.player2Email = player2Email;
	}

	public String getPlayerNTurn() {
		return playerNTurn;
	}

	public void setPlayerNTurn(String playerNTurn) {
		this.playerNTurn = playerNTurn;
	}

	public Integer getPlayer1Degree() {
		return player1Degree;
	}

	public void setPlayer1Degree(Integer player1Degree) {
		this.player1Degree = player1Degree;
	}

	public Integer getPlayer2Degree() {
		return player2Degree;
	}

	public void setPlayer2Degree(Integer player2Degree) {
		this.player2Degree = player2Degree;
	}

	public String getPlayer1Color() {
		return player1Color;
	}

	public void setPlayer1Color(String player1Color) {
		this.player1Color = player1Color;
	}

	public String getPlayer2Color() {
		return player2Color;
	}

	public void setPlayer2Color(String player2Color) {
		this.player2Color = player2Color;
	}

	
	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public boolean isEndOfTheGame() {
		return endOfTheGame;
	}

	public void setEndOfTheGame(boolean endOfTheGame) {
		this.endOfTheGame = endOfTheGame;
	}

	public String getLoser() {
		return loser;
	}

	public void setLoser(String loser) {
		this.loser = loser;
	}

	@Override
	public String toString() {
		return "Gomoku [player1Name=" + player1Name + ", player2Name=" + player2Name
				+ ", player1Email=" + player1Email + ", player1Color=" + player1Color + ", player2Email=" + player2Email
				+ ", player1Degree=" + player1Degree + ", player2Degree=" + player2Degree + ", player2Color="
				+ player2Color + ", playerNTurn=" + playerNTurn + "]";
	}
	
	
}

	
