package tw.com.eeit162.meepleMasters.lyh.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.eeit162.meepleMasters.lyh.model.bean.CardOwned;

public interface CardOwnedDao extends JpaRepository<CardOwned, Integer> {
	
	List<CardOwned> findByFkMemberId(Integer fkMemberId);
	
	
	
}
