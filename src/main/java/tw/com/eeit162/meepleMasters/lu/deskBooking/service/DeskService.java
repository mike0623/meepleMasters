package tw.com.eeit162.meepleMasters.lu.deskBooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.eeit162.meepleMasters.lu.deskBooking.model.DeskBean;
import tw.com.eeit162.meepleMasters.lu.deskBooking.model.DeskDao;

@Service
@Transactional
public class DeskService {
	
	@Autowired
	private DeskDao deskDao;
	
	public void insertDesk(DeskBean desk) {
		deskDao.save(desk);
	}
	
	public List<DeskBean> findAllDesks() {
		return deskDao.findAll();
	}
	
	public DeskBean getDeskById(int deskId) {
		return deskDao.findById(deskId).orElse(null);
	}
	public String getDeskTypeById(int deskId) {
		DeskBean desk = deskDao.getDeskById(deskId);
			if (desk != null) {
		        return desk.getDeskType();
		    }
		    return null;
	}

	
	public void updateDesk(DeskBean desk) {
		deskDao.save(desk);
	}
	
	public void deleteDeskById(int deskId) {
		deskDao.deleteById(deskId);
	}
	
	public int getDeskPrice(int deskType) {
	    switch(deskType) {
	        case 1:
	            return 100;
	        case 2:
	            return 200;
	        case 3:
	            return 300;
	        default:
	            return 0;
	    }
	}

}