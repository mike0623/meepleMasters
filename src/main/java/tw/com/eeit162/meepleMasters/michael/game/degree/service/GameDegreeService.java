package tw.com.eeit162.meepleMasters.michael.game.degree.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.michael.game.degree.model.GameDegree;
import tw.com.eeit162.meepleMasters.michael.game.degree.model.GameDegreeDao;

@Service
public class GameDegreeService {
	
	@Autowired
	private GameDegreeDao gameDegreeDao;
	
	public GameDegree findGameDegreeByBoth(Integer fkMember,Integer fkProduct) {
		return gameDegreeDao.findGameDegreeByBoth(fkMember, fkProduct);
	}
	
	
}
