package tw.com.eeit162.meepleMasters.jim.mall.model.bean;

import java.util.List;

public class FaveriteGameList {
	
	private List<Product> faveriteGameList;
	
	

	public FaveriteGameList() {
	}

	public FaveriteGameList(List<Product> faveriteGameList) {
		this.faveriteGameList = faveriteGameList;
	}

	public List<Product> getFaveriteGameList() {
		return faveriteGameList;
	}

	public void setFaveriteGameList(List<Product> faveriteGameList) {
		this.faveriteGameList = faveriteGameList;
	}
	
	

}
