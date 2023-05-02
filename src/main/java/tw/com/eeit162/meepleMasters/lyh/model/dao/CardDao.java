package tw.com.eeit162.meepleMasters.lyh.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.eeit162.meepleMasters.lyh.model.bean.Card;

public interface CardDao extends JpaRepository<Card, Integer> {
	


}
