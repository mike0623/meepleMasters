package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ResponseReviewDao extends JpaRepository<ResponseReview, Integer> {

	@Transactional
	@Modifying
	@Query("update ResponseReview set responseReview = :responseReview where responseReviewId = :responseReviewId")
	void updateResponseReview(
			@Param("responseReviewId")Integer responseReviewId,
			@Param("responseReview")Boolean responseReview
			);
	
}
