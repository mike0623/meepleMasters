package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ResponseDao extends JpaRepository<Response, Integer> {

	@Transactional
	@Modifying
	@Query("update Response set responseUpdatedDate = :responseUpdatedDate, responseContent = :content where responseId = :responseId")	
	void updateResponseContentById(
			@Param("responseId")Integer responseId,
			@Param("responseUpdatedDate")Date responseUpdatedDate,
			@Param("content")String content
			);
	
//	--------------------------------------------------------------------------------
	
	@Query("from Response where "
			+ " ( :memberId is null or fk_memberId = :memberId ) "
			+ "and ( :articleId is null or fk_articleId = :articleId )"
			)
	List<Response> selectResponse(
			@Param("memberId")Integer memberId,
			@Param("articleId") Integer articleId
			);
//	convert(nvarchar,responseCreatedDate,120) like %:createdDate%
	
}
