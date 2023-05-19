package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowArticleDao extends JpaRepository<FollowArticle, Integer> {

	List<FollowArticle> findByFkMemberId(Integer fkMemberId);
	
	List<FollowArticle> findByFkArticleId(Integer fkMemberId);
	
}
