package tw.com.eeit162.meepleMasters.shoehorn.forum.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jack.model.dao.MemberDao;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.jim.mall.model.dao.ProductDAO;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.Article;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleComment;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleCommentDao;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleCommentReview;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleCommentReviewDao;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleDao;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleReview;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleReviewDao;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.FaviroteArticle;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.FaviroteArticleDao;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.FollowArticle;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.FollowArticleDao;

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
	@Autowired
	private FollowArticleDao followArticleDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private ProductDAO productDAO;
	
//	-----Article-----
	
//	新增文章
	public void	createArticle(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		Article article = new Article();
		article.setFkMemberId(jsonObject.getInt("memberId"));
		article.setFkProductId(jsonObject.getInt("productId"));
		article.setArticleCreatedDate(
				Date.from(LocalDateTime.now()
//						.truncatedTo(ChronoUnit.SECONDS) //將毫秒清0，但仍有小數點(.000)
						.atZone(ZoneId.systemDefault())
						.toInstant())
				);
		article.setArticleTitle(jsonObject.getString("title"));
//		content內的Image另外包?
		article.setArticleContent(
				jsonObject.getString("content"));
		articleDao.save(article);
	}
	
//	修改文章
//	非本人不得修改文章，如何get 目前登入帳號?
	public void updateArticle(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		String articleContent = jsonObject.getString("content");
		
		Date articleUpdatedDate =
				Date.from(LocalDateTime.now()
						.atZone(ZoneId.systemDefault())
						.toInstant());
		
		articleDao.updateArticleContentById(jsonObject.getInt("articleId"), articleUpdatedDate, articleContent);
		
	}
	
//	查詢文章(動態查詢可加強)
//	用不到，可用下方dynamicSelect()取代
	public List<Article> selectArticle(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		List<Article> resultList = null;
		
		// 全部文章
		if (jsonObject.isEmpty()) {
			resultList = articleDao.findAll();
		}
		
		// 文章 by memberId
		if (jsonObject.has("memberId") && jsonObject.length()==1) {
			int memberId = jsonObject.getInt("memberId");
			resultList = articleDao.selectArticleByMemberId(memberId);
		};
		
		// 文章 by productId
		if (jsonObject.has("productId") && jsonObject.length()==1) {
			int productId = jsonObject.getInt("productId");
			resultList = articleDao.selectArticleByProductId(productId);
		};
		
		// 文章 by articleId
		if (jsonObject.has("articleId") && jsonObject.length()==1) {
			int articleId = jsonObject.getInt("articleId");
			resultList = articleDao.selectArticleByArticleId(articleId);
		};
		
		// 文章 by title	，可模糊搜尋
		if (jsonObject.has("articleTitle") && jsonObject.length()==1) {
			String articleTitle = jsonObject.getString("articleTitle");
			resultList = articleDao.selectArticleByArticleTitle(articleTitle);
		};
		
		return resultList;
	}

//	動態多條件查詢
//	尚未加入日期的條件搜尋、標題&日期的模糊搜尋
	public List<Article> dynamicSelect(String body) {
		
		JSONObject jsonObject = new JSONObject(body);
		
		List<Article> dynamicSelect = articleDao.dynamicSelect(
				jsonObject.has("articleId")?jsonObject.getInt("articleId"):null,
				jsonObject.has("memberId")?jsonObject.getInt("memberId"):null,
				jsonObject.has("productId")?jsonObject.getInt("productId"):null,
				jsonObject.has("articleTitle")?jsonObject.getString("articleTitle"):null
				);
		
		return dynamicSelect;
	}
	
	
	
//	刪除文章
	public void deleteArticle(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		int articleId = jsonObject.getInt("articleId");
		
		articleDao.deleteById(articleId);
	}
	
	
//	-----ArticleReview-----
	
//	更新文章評價	如果無該筆紀錄就新增
//	評價:	articleReview	無評:null		好評:1	負評:0
	public void updateArticleReview(String jsonString) {

		JSONObject jsonObject = new JSONObject(jsonString);

		ArticleReview articleReview = new ArticleReview();
		
		if (jsonObject.has("articleReviewId") && !articleReviewDao.findById(jsonObject.getInt("articleReviewId")).isEmpty()) {
//		更新
			articleReviewDao.updateArticleReview(jsonObject.getInt("articleReviewId"),
					jsonObject.isNull("articleReview")?null:jsonObject.getBoolean("articleReview"));
		} else {
//		新增
			articleReview.setFkArticleId(jsonObject.getInt("articleId"));
			articleReview.setFkMemberId(jsonObject.getInt("memberId"));
			articleReview.setArticleReview(
					jsonObject.isNull("articleReview")?null:jsonObject.getBoolean("articleReview")
					);
			articleReviewDao.save(articleReview);
		}
		
	}
	
//	查詢文章評價
	public Boolean selectArticleReview(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		Optional<ArticleReview> findById = articleReviewDao.findById(jsonObject.getInt("articleReviewId"));
		
		return findById.isPresent()?findById.get().getArticleReview():null;
		
	}
	
//	查詢文章評價Id
	public ArticleReview selectArticleReviewId(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		ArticleReview articleReview = articleReviewDao.selectArticleReviewId(jsonObject.getInt("memberId"), jsonObject.getInt("articleId"));
		
		return articleReview;
	}
	
//	查詢文章評價總數byArticleId
	public Integer selectArticleGoodReviewCount(Integer articleId) {
		
		return articleReviewDao.selectArticleGoodReviewCount(articleId);
		
	}
	
	public Integer selectArticleBadReviewCount(Integer articleId) {
		
		return articleReviewDao.selectArticleBadReviewCount(articleId);
		
	}
	

//	-----ArticleComment-----

//	新增文章留言
	public void createArticleComment(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		ArticleComment articleComment = new ArticleComment();
		
		articleComment.setFkMemberId(jsonObject.getInt("memberId"));
		articleComment.setFkArticleId(jsonObject.getInt("articleId"));
		articleComment.setArticleCommentCreatedDate(
				Date.from(LocalDateTime.now()
						.atZone(ZoneId.systemDefault())
						.toInstant())				
				);
		articleComment.setArticleCommentContent(jsonObject.getString("content"));
		articleCommentDao.save(articleComment);		
		
	}
	
//	修改文章留言	
	
	public void updateArticleComment(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		String articleCommentContent = jsonObject.getString("content");
		
		Date articleCommentUpdatedDate =
				Date.from(LocalDateTime.now()
						.atZone(ZoneId.systemDefault())
						.toInstant());
		
		articleCommentDao.updateArticleCommentById(jsonObject.getInt("articleCommentId"), articleCommentUpdatedDate, articleCommentContent);
		
	}
	
//	刪除文章留言
	
	public void deleteArticleComment (String body) {
		
		JSONObject jsonObject = new JSONObject(body);
		
		articleCommentDao.deleteById(jsonObject.getInt("articleCommentId"));
		
	}
	
//	查詢文章留言
//	動態多條件查詢
//	尚未加入日期的條件搜尋、標題&日期的模糊搜尋
	public List<ArticleComment> selectArticleComment(String body) {
		
		JSONObject jsonObject = new JSONObject(body);
		
		List<ArticleComment> dynamicSelect = articleCommentDao.selectArticleComment(
				jsonObject.has("articleCommentId")?jsonObject.getInt("articleCommentId"):null,
				jsonObject.has("memberId")?jsonObject.getInt("memberId"):null,
				jsonObject.has("articleId")?jsonObject.getInt("articleId"):null
				);
		
		return dynamicSelect;
	}
	
//	-----ArticleCommentReview-----
	
//	更新文章留言評價	如果無該筆紀錄就新增
//	評價:	articleCommentReview	無評:null		好評:1	負評:0
	public void updateArticleCommentReview(String jsonString) {

		JSONObject jsonObject = new JSONObject(jsonString);

		ArticleCommentReview articleCommentReview = new ArticleCommentReview();
		
		if (jsonObject.has("articleCommentReviewId") && !articleCommentReviewDao.findById(jsonObject.getInt("articleCommentReviewId")).isEmpty()) {
//		更新
			articleCommentReviewDao.updateArticleCommentReview(jsonObject.getInt("articleCommentReviewId"),
					jsonObject.isNull("commentReview")?null:jsonObject.getBoolean("commentReview"));
		} else {
//		新增
			articleCommentReview.setFkArticleCommentId(jsonObject.getInt("articleCommentId"));
			articleCommentReview.setFkMemberId(jsonObject.getInt("memberId"));
			articleCommentReview.setCommentReview(
					jsonObject.isNull("commentReview")?null:jsonObject.getBoolean("commentReview")
					);
			articleCommentReviewDao.save(articleCommentReview);
		}
		
	}
	
//	查詢文章留言評價
	public Boolean selectArticleCommentReview(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		Optional<ArticleCommentReview> findById = articleCommentReviewDao.findById(jsonObject.getInt("articleCommentReviewId"));
		
		return findById.isPresent()?findById.get().getCommentReview():null;

	}
	
//	查詢文章留言評價ByMemberId
	public ArticleCommentReview selectArticleCommentReviewByMemberId(Integer memberId,Integer articleCommentId) {
		return articleCommentReviewDao.selectArticleCommentReviewByMemberId(memberId, articleCommentId);
	}
	
	
	
//	查詢文章留言評價總數byArticleCommentId
	public Integer selectArticleCommentGoodReviewCount(Integer articleCommentId) {
		
		return articleCommentReviewDao.selectArticleCommentGoodReviewCount(articleCommentId);
		
	}
	
	public Integer selectArticleCommentBadReviewCount(Integer articleCommentId) {
		
		return articleCommentReviewDao.selectArticleCommentBadReviewCount(articleCommentId);
		
	}
	
	
//	-----FaviroteArticle-----
	
//	查詢最愛文章(以會員搜尋)
	public List<FaviroteArticle> selectFaviroteArticle(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		return faviroteArticleDao.findByFkMemberId(jsonObject.getInt("memberId"));
	}
	
//	查詢最愛文章(以文章搜尋)
	public List<FaviroteArticle> selectFaviroteArticleByArticle(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		return faviroteArticleDao.findByFkArticleId(jsonObject.getInt("articleId"));
	}
	
//	更新最愛文章
	public void updateFaviroteArticle(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		FaviroteArticle faviroteArticle = new FaviroteArticle();
		
		if (jsonObject.has("faviroteArticleId") && !faviroteArticleDao.findById(jsonObject.getInt("faviroteArticleId")).isEmpty()) {
			
			faviroteArticleDao.deleteById(jsonObject.getInt("faviroteArticleId"));
			
		} else {
			faviroteArticle.setFkMemberId(jsonObject.getInt("memberId"));
			faviroteArticle.setFkArticleId(jsonObject.getInt("articleId"));
				
			faviroteArticleDao.save(faviroteArticle);
		}	
		
	}
	
//	刪除最愛文章
	public void deleteFaviroteArticleByArticleId(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		faviroteArticleDao.deleteByFkArticleId(jsonObject.getInt("articleId"));

	}
	
//	-----FollowArticle-----
	
//	查詢追蹤文章
	public List<FollowArticle> selectFollowArticle(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		return followArticleDao.findByFkMemberId(jsonObject.getInt("memberId"));
	}
	
//	更新追蹤文章
	public void updateFollowArticle(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		FollowArticle followArticle = new FollowArticle();
		
		if (jsonObject.has("followArticleId") && !followArticleDao.findById(jsonObject.getInt("followArticleId")).isEmpty()) {
			
			followArticleDao.deleteById(jsonObject.getInt("followArticleId"));
			
		} else {
			followArticle.setFkMemberId(jsonObject.getInt("memberId"));
			followArticle.setFkArticleId(jsonObject.getInt("articleId"));
				
			followArticleDao.save(followArticle);
		}	
		
	}
	
//	查詢追蹤文章(以文章搜尋)
	public List<FollowArticle> selectFollowArticleByArticle(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		return followArticleDao.findByFkArticleId(jsonObject.getInt("articleId"));
	}
	
//	取得memberName
	public String getMemberName(Integer memberId) {
		return memberDao.findById(memberId).get().getMemberName();
	}
	
//	取得productName
	public String getProductName(Integer producyId) {
		return productDAO.findById(producyId).get().getProductName();
	}
	
//	取得所有member
	public List<Member> getAllMemberName() {
		return memberDao.findAll();
	}
	
//	取得所有product
	public List<Product> getAllProductName() {
		return productDAO.findAll();
	}
	
	public Integer getInputMemberId(String name) {
		
		if (name=="") {
			System.out.println("空的");
			return null;
		}
		
		if (memberDao.findMemberByName(name).isEmpty()) {
			return null;
		}
		
		return memberDao.findMemberByName(name).get(0).getMemberId();
	}
}









