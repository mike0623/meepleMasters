package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleDao extends JpaRepository<Article, Integer> {

	@Transactional
	@Modifying
	@Query("update Article set articleUpdatedDate = :articleUpdatedDate, articleContent = :articleContent where articleId = :articleId")
	void updateArticleContentById(
			@Param("articleId")Integer articleId,
			@Param("articleUpdatedDate")Date articleUpdatedDate,
			@Param("articleContent")String articleContent
			);
	
//	--------------------------------------------------------------------------------

	@Query("from Article where fk_memberId = :memberId")
	List<Article> selectArticleByMemberId(
			@Param("memberId")Integer memberId
			);

	@Query("from Article where fk_productId = :productId")
	List<Article> selectArticleByProductId(
			@Param("productId") Integer productId
			);

	@Query("from Article where articleId = :articleId")
	List<Article> selectArticleByArticleId(
			@Param("articleId") Integer articleId
			);

	@Query("from Article where articleTitle like %:articleTitle%")
	List<Article> selectArticleByArticleTitle(
			@Param("articleTitle") String articleTitle
			);
	
//	--------------------------------------------------------------------------------
	
	@Query("from Article where "
			+ "( :articleId is null or articleId = :articleId ) "
			+ "and ( :memberId is null or fk_memberId = :memberId ) "
			+ "and ( :productId is null or fk_productId = :productId )"
			+ "and ( :articleTitle is null or articleTitle like %:articleTitle% )")
	List<Article> dynamicSelect(
			@Param("articleId") Integer articleId,
			@Param("memberId")Integer memberId,
			@Param("productId") Integer productId,
			@Param("articleTitle") String articleTitle
			);
	
	
	
}
