package tw.com.eeit162.meepleMasters.shoehorn.forum.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleCommentDao extends JpaRepository<ArticleComment, Integer> {

	@Transactional
	@Modifying
	@Query("update ArticleComment set articleCommentUpdatedDate = :articleCommentUpdatedDate, articleCommentContent = :articleCommentContent where articleCommentId = :articleCommentId")
	void updateArticleCommentById(
			@Param("articleCommentId")Integer articleCommentId,
			@Param("articleCommentUpdatedDate")Date articleCommentUpdatedDate,
			@Param("articleCommentContent")String articleCommentContent
			);
	
	
	@Query("from ArticleComment where "
			+ "( :articleCommentId is null or articleCommentId = :articleCommentId ) "
			+ "and ( :memberId is null or fk_memberId = :memberId ) "
			+ "and ( :articleId is null or fk_articleId = :articleId )")
	List<ArticleComment> selectArticleComment(
			@Param("articleCommentId") Integer articleCommentId,
			@Param("memberId")Integer memberId,
			@Param("articleId") Integer articleId
			);
}
