package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ResponseCommentReviewDao extends JpaRepository<ResponseCommentReview, Integer> {

	@Transactional
	@Modifying
	@Query("update ResponseCommentReview set commentReview = :commentReview where responseCommentReviewId = :responseCommentReviewId")
	void updateResponseCommentReview(
			@Param("responseCommentReviewId")Integer responseCommentReviewId,
			@Param("commentReview")Boolean commentReview
			);
	
}
