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
	
}
