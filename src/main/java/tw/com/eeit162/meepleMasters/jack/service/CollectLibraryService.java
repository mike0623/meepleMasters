package tw.com.eeit162.meepleMasters.jack.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jack.model.dao.CollectLibraryDao;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;

@Service
public class CollectLibraryService {
	@Autowired
	private CollectLibraryDao collectLibraryDao;
	
	
	public List<Object[]> findMemberCollect(Integer memberId) {
		
		return collectLibraryDao.findMemberCollect(memberId);
	}
}
