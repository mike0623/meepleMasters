package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ResponseCommentDao extends JpaRepository<ResponseComment, Integer> {

	@Transactional
	@Modifying
	@Query("update ResponseComment set responseCommentUpdatedDate = :responseCommentUpdatedDate, responseCommentContent = :responseCommentContent where responseCommentId = :responseCommentId")
	void updateResponseCommentById(
			@Param("responseCommentId")Integer responseCommentId,
			@Param("responseCommentUpdatedDate")Date responseCommentUpdatedDate,
			@Param("responseCommentContent")String responseCommentContent
			);
	
	
	@Query("from ResponseComment where "
			+ "( :responseCommentId is null or responseCommentId = :responseCommentId ) "
			+ "and ( :memberId is null or fk_memberId = :memberId ) "
			+ "and ( :responseId is null or fk_responseId = :responseId )")
	List<ResponseComment> selectResponseComment(
			@Param("responseCommentId") Integer responseCommentId,
			@Param("memberId")Integer memberId,
			@Param("responseId") Integer responseId
			);
	
}
