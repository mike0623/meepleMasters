package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleReviewDao extends JpaRepository<ArticleReview, Integer> {

	
	@Transactional
	@Modifying
	@Query("update ArticleReview set articleReview = :articleReview where articleReviewId = :articleReviewId")
	void updateArticleReview(
			@Param("articleReviewId")Integer articleReviewId,
			@Param("articleReview")Boolean articleReview
			);
	
//	------------------------------------------------------------------------------
	
	@Query("select count(*) from ArticleReview where fk_articleId = :articleId and articleReview = 1")
	Integer selectArticleGoodReviewCount(
			@Param("articleId")Integer articleId
			);
	
	@Query("select count(*) from ArticleReview where fk_articleId = :articleId and articleReview = 0")
	Integer selectArticleBadReviewCount(
			@Param("articleId")Integer articleId
			);
	
//	------------------------------------------------------------------------------
	
	@Query("from ArticleReview where fk_articleId = :articleId and fk_memberId = :memberId")
	ArticleReview selectArticleReviewId(
			@Param("memberId")Integer memberId,
			@Param("articleId")Integer articleId
			);
	
}
