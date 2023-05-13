package tw.com.eeit162.meepleMasters.jack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.jack.model.dao.CollectLibraryDao;

@Service
public class CollectLibraryService {
	@Autowired
	private CollectLibraryDao collectLibraryDao;
	
	
	public List<Object[]> findMemberCollect(Integer memberId) {
		
		return collectLibraryDao.findMemberCollect(memberId);
	}
}
