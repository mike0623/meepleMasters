package tw.com.eeit162.meepleMasters.jim.mall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.jim.mall.model.bean.FavoriteGame;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.FavoriteGameDAO;

@Service
public class FavoriteGameService {

	@Autowired
	private FavoriteGameDAO fgDAO;

	public FavoriteGame addFavoriteGame(Integer productId, Integer memberId) {
		FavoriteGame favoriteGame = new FavoriteGame();

		favoriteGame.setFkProductId(productId);
		favoriteGame.setFkMemberId(memberId);

		return fgDAO.save(favoriteGame);
	}

	public List<FavoriteGame> findFavoriteGameList(Integer memberId) {
		return fgDAO.findByFkMemberId(memberId);
	}
}
