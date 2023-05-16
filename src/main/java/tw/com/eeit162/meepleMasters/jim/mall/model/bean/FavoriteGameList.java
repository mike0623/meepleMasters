package tw.com.eeit162.meepleMasters.jim.mall.model.bean;

import java.util.List;

public class FavoriteGameList {
	
	private List<Product> favoriteGameList;
	
	

	public FavoriteGameList() {
	}

	public FavoriteGameList(List<Product> faveriteGameList) {
		this.favoriteGameList = faveriteGameList;
	}

	public List<Product> getFaveriteGameList() {
		return favoriteGameList;
	}

	public void setFaveriteGameList(List<Product> faveriteGameList) {
		this.favoriteGameList = faveriteGameList;
	}
	
	

}
