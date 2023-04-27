package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleDao extends JpaRepository<Article, Integer> {

	@Transactional
	@Modifying
	@Query("update Article set articleId = :articleId, articleUpdatedDate = :articleUpdatedDate, articleContent = :articleContent")
	void updateArticleContentById(
			@Param("articleId")Integer articleId,
			@Param("articleUpdatedDate")Date articleUpdatedDate,
			@Param("articleContent")byte[] articleContent
			);
	
}
