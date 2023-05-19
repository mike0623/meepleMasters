package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface FaviroteArticleDao extends JpaRepository<FaviroteArticle, Integer> {

	List<FaviroteArticle> findByFkMemberId(Integer fkMemberId);
	
	List<FaviroteArticle> findByFkArticleId(Integer fkArticleId);

	@Transactional
	@Modifying
	void deleteByFkArticleId(Integer fkArticleId);
}
