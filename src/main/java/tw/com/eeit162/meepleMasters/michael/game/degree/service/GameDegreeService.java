package tw.com.eeit162.meepleMasters.michael.game.degree.service;

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
	
	
}
