package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleCommentReviewDao extends JpaRepository<ArticleCommentReview, Integer> {

	@Transactional
	@Modifying
	@Query("update ArticleCommentReview set commentReview = :commentReview where articleCommentReviewId = :articleCommentReviewId")
	void updateArticleCommentReview(
			@Param("articleCommentReviewId")Integer articleCommentReviewId,
			@Param("commentReview")Boolean commentReview
			);
	
//	-----------------------------------------------------------------------------
	
	@Query("select count(*) from ArticleCommentReview where fk_articleCommentId = :articleCommentId and commentReview = 1")
	Integer selectArticleCommentGoodReviewCount(
			@Param("articleCommentId")Integer articleCommentId
			);
	
	@Query("select count(*) from ArticleCommentReview where fk_articleCommentId = :articleCommentId and commentReview = 0")
	Integer selectArticleCommentBadReviewCount(
			@Param("articleCommentId")Integer articleCommentId
			);
	
//	-----------------------------------------------------------------------------
	@Query("from ArticleCommentReview where fk_memberId = :memberId and fk_articleCommentId = :articleCommentId")
	ArticleCommentReview selectArticleCommentReviewByMemberId(
			@Param("memberId")Integer memberId,
			@Param("articleCommentId")Integer articleCommentId
			);
	
	
}
