package tw.com.eeit162.meepleMasters.michael.game;

import java.util.ArrayList;
import java.util.List;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;

public class Game {
	
	List<Member> players = new ArrayList<Member>();
	
	String gameName;
	
	String hostEmail;
	
	Integer maxNumOfPlayer;

	Integer minNumOfPlayer;
	
	Integer finalNumOfPlayer;
	
	Integer gameStatus; //0代表尚未創建，1代表創建中，2代表已開放所有玩家，3代表遊玩中

	public String addPlayer(Member member) {
		if(players.size() >= maxNumOfPlayer) {
			return "超過上線人數";
		}
		players.add(member);
		return "加入成功";
	}
	
	public void addHost(Member member) {
		this.hostEmail = member.getMemberEmail();
		addPlayer(member);
	}
	
	public void removePlayer(String memberEmail) {
		for(int i = 1;i<players.size();i++) {
			System.out.println("把玩家從桌子移除"+players.get(i).getMemberEmail()+" "+memberEmail);
			if(players.get(i).getMemberEmail().equals(memberEmail)) {
				players.remove(i);
				break;
			}
		}
	}
	
	
	
	
	public Integer getMaxNumOfPlayer() {
		return maxNumOfPlayer;
	}

	public void setMaxNumOfPlayer(Integer maxNumOfPlayer) {
		this.maxNumOfPlayer = maxNumOfPlayer;
	}

	public Integer getMinNumOfPlayer() {
		return minNumOfPlayer;
	}

	public void setMinNumOfPlayer(Integer minNumOfPlayer) {
		this.minNumOfPlayer = minNumOfPlayer;
	}

	public String getHostEmail() {
		return hostEmail;
	}

	public void setHostEmail(String hostEmail) {
		this.hostEmail = hostEmail;
	}

	public List<Member> getPlayers() {
		return players;
	}

	public Integer getFinalNumOfPlayer() {
		return finalNumOfPlayer;
	}

	public void setFinalNumOfPlayer(Integer finalNumOfPlayer) {
		this.finalNumOfPlayer = finalNumOfPlayer;
	}

	@Override
	public String toString() {
		return "Game [players=" + players + ", hostEmail=" + hostEmail + ", maxNumOfPlayer=" + maxNumOfPlayer
				+ ", minNumOfPlayer=" + minNumOfPlayer + ", finalNumOfPlayer=" + finalNumOfPlayer + "]";
	}

	public Integer getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(Integer gameStatus) {
		this.gameStatus = gameStatus;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	


	
	
	
	
	
	
	
}
