package tw.com.eeit162.meepleMasters.michael.game.degree.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.michael.game.degree.model.GameDegree;
import tw.com.eeit162.meepleMasters.michael.game.degree.model.GameDegreeDao;

@Service
public class GameDegreeService {
	
	@Autowired
	private GameDegreeDao gameDegreeDao;
	
	//查詢，若無資料就新增一筆0分的資料
	public GameDegree findGameDegreeByBoth(Integer fkMember,Integer fkProduct) {
		GameDegree firstFind = gameDegreeDao.findGameDegreeByBoth(fkMember, fkProduct);
		if(firstFind != null) {
			return firstFind;
		}
		GameDegree newGameDegree = new GameDegree();
		newGameDegree.setFkMemberId(fkMember);
		newGameDegree.setFkProductId(fkProduct);
		newGameDegree.setScore(0);
		GameDegree secondFind = gameDegreeDao.save(newGameDegree);
		return secondFind;
	}
	
	//遊戲結束時，更改分數
	@Transactional
	public Integer updateGameDegreeByBoth(boolean isWin,Integer averageScore,Integer fkMember,Integer fkProduct) {
		GameDegree gameDegree = gameDegreeDao.findGameDegreeByBoth(fkMember,fkProduct);
		Integer oldScore = gameDegree.getScore();
		Integer newScore = 0;
		double result = (double)averageScore;
		if(isWin) {
			Integer plusScore = (int)(Math.ceil(50*(2-(oldScore/result))));
			if(plusScore <= 0) {
				plusScore = 1;
			}
			if(plusScore >= 100) {
				plusScore = 100;
			}
			newScore = oldScore + plusScore;
		}
		if(!isWin) {
			Integer minusScore = (int)(Math.ceil(50*((oldScore/result))));
			if(minusScore >= 100) {
				minusScore = 100;
			}
			newScore = oldScore - minusScore;
			if(newScore <= 0) {
				newScore = 1;
			}
		}
		gameDegreeDao.updateGameDegreeByBoth(newScore, fkMember, fkProduct);
		return newScore-oldScore;
	}
	
}
