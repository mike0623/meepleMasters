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

	public String addFavoriteGame(Integer productId, Integer memberId) {

		FavoriteGame fg = fgDAO.findByFkMemberIdAndFkProductId(memberId, productId);

		if (fg != null) {
			fgDAO.deleteById(fg.getFavoriteGameId());
			return "dislike";
		}

		FavoriteGame favoriteGame = new FavoriteGame();
		favoriteGame.setFkProductId(productId);
		favoriteGame.setFkMemberId(memberId);
		fgDAO.save(favoriteGame);

		return "like";
	}

	public List<FavoriteGame> findFavoriteGameList(Integer memberId) {
		return fgDAO.findByFkMemberId(memberId);
	}
}
