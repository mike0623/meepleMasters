package tw.com.eeit162.meepleMasters.shoehorn.forum.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.shoehorn.forum.model.Article;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleCommentDao;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleCommentReviewDao;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleDao;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleReview;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleReviewDao;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.FaviroteArticleDao;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ArticleReviewDao  articleReviewDao;
	@Autowired
	private ArticleCommentDao articleCommentDao;
	@Autowired
	private ArticleCommentReviewDao articleCommentReviewDao;
	@Autowired
	private FaviroteArticleDao faviroteArticleDao;
	
//	-----Article-----
	
//	新增文章
	public void	createArticle(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		Article article = new Article();
		article.setFkMemberId(jsonObject.getInt("memberId"));
		article.setFkProductId(jsonObject.getInt("productId"));
		article.setArticleCreatedDate(
				Date.from(LocalDateTime.now()
						.atZone(ZoneId.systemDefault())
						.toInstant())
				);
		article.setArticleTitle("articleTitle");
//		content內的Image另外包?
		article.setArticleContent(
				jsonObject.getString("articleContent")
				.getBytes());
		articleDao.save(article);
	}
	
//	修改文章
//	非本人不得修改文章，如何get 目前登入帳號?
	public void updateArticle(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		Optional<Article> findById = articleDao.findById(jsonObject.getInt("articleId"));
		
		Article article = findById.get();
		
		Integer articleId = article.getArticleId();
		
		byte[] articleContent = article.getArticleContent();
		
		Date articleUpdatedDate =
				Date.from(LocalDateTime.now()
						.atZone(ZoneId.systemDefault())
						.toInstant());
		
		articleDao.updateArticleContentById(articleId, articleUpdatedDate, articleContent);
		
	}
	
//	查詢文章(未完成)
	public Article selectArticle() {
		
		return null;
		
	}
	
//	刪除文章
	public void deleteArticle(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		int articleId = jsonObject.getInt("articleId");
		
		articleDao.deleteById(articleId);
	}
	
	
//	-----ArticleReview-----
	
//	新增文章評論
	public void createArticleReview(String jsonString) {
//		1.不可同時留下 好評、負評
//		2.更改評價時，自動設置 另一評價 為null
		JSONObject jsonObject = new JSONObject(jsonString);
		
		String articleGoodReview = jsonObject.getString("goodReview");
		String articleBadReview = jsonObject.getString("badReview");
		
		if (articleGoodReview!=null && !articleGoodReview.isEmpty()) {
			if (articleBadReview!=null && !articleBadReview.isEmpty()) {
				
			}
		}
		ArticleReview articleReview = new ArticleReview();
		
		articleReview.setFkArticleId(jsonObject.getInt("articleId"));
		articleReview.setFkMemberId(jsonObject.getInt("memberId"));
		articleReview.setArticleGoodReview(jsonObject.getString("goodReview"));
		articleReview.setArticleBadReview(jsonObject.getString("badReview"));
		
		articleReviewDao.save(articleReview);
	}

//	-----ArticleComment-----
//	-----ArticleCommentReview-----
//	-----FaviroteArticle-----
}









