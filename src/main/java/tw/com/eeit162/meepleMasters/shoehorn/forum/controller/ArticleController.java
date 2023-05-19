package tw.com.eeit162.meepleMasters.shoehorn.forum.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.Article;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleComment;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleReview;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.FaviroteArticle;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.FollowArticle;
import tw.com.eeit162.meepleMasters.shoehorn.forum.service.ArticleService;

@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
//	起始forum頁面----------------------------------------------------------------
	
	@GetMapping("/forum")
	public String forumIndexPage(Model model) {
		JSONObject jsonObject = new JSONObject();

		List<Integer> goodReviewCount = new ArrayList<Integer>();
		List<Integer> badReviewCount = new ArrayList<Integer>();
		List<Article> dynamicSelect = articleService.dynamicSelect(jsonObject.toString());
		
		for(Article article:dynamicSelect) {
			Integer goodCount = articleService.selectArticleGoodReviewCount(article.getArticleId());
			goodReviewCount.add(goodCount);
			Integer badCount = articleService.selectArticleBadReviewCount(article.getArticleId());
			badReviewCount.add(badCount);
		}
		
		model.addAttribute("articleList",dynamicSelect);
		model.addAttribute("goodReviewCount",goodReviewCount);
		model.addAttribute("badReviewCount",badReviewCount);
		
		return "shoehorn/forum";
	}
			
//	多條件查詢後顯示文章
	
	@PostMapping("/forum")
	public String forumIndexPage(
//			@RequestBody(required = false) String body,
			@RequestParam(name = "poster",required = false) Integer poster,
			@RequestParam(name = "product",required = false) Integer product,
			@RequestParam(name = "title",required = false) String title,
			Model model) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("memberId", poster);
		jsonObject.put("productId", product);
		jsonObject.put("articleTitle", title);

		List<Integer> goodReviewCount = new ArrayList<Integer>();
		List<Integer> badReviewCount = new ArrayList<Integer>();
		List<Article> dynamicSelect = articleService.dynamicSelect(jsonObject.toString());
		for(Article article:dynamicSelect) {
			Integer goodCount = articleService.selectArticleGoodReviewCount(article.getArticleId());
			goodReviewCount.add(goodCount);
			Integer badCount = articleService.selectArticleBadReviewCount(article.getArticleId());
			badReviewCount.add(badCount);
		}
		model.addAttribute("articleList",dynamicSelect);
		model.addAttribute("goodReviewCount",goodReviewCount);
		model.addAttribute("badReviewCount",badReviewCount);
		
		return "shoehorn/forum";
	}
	
//	起始新增文章頁面------------------------------------------
	
	@GetMapping("/newArticle")
	public String newArticle() {
		return "shoehorn/newArticle";
	}
	
//	新增文章---------------------------------------------
	
	@PostMapping("/newArticle")
	public String newArticle(
			@RequestParam(name = "poster",required = false) Integer poster,
			@RequestParam(name = "product",required = false) Integer product,
			@RequestParam(name = "title",required = false) String title,
			@RequestParam(name = "content",required = false) String content) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("memberId", poster);
		jsonObject.put("productId", product);
		jsonObject.put("title", title);
		jsonObject.put("content", content);

		articleService.createArticle(jsonObject.toString());
		
		return "redirect:/forum";
	}
	
//	進入文章頁面------------------------------------------
	
	@GetMapping("/article")
	public String article(@RequestParam("articleId")Integer articleId,
			Model model,
			HttpSession session
			) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("articleId", articleId);
		
		List<Article> list = articleService.dynamicSelect(jsonObject.toString());
		
		Article article = list.get(0);
		
		model.addAttribute("article", article);
		
		
//		確認是否登入、登入者為文章發布者才能修改
		Member member = (Member)session.getAttribute("member");
		model.addAttribute("author", false);
		if (member!=null) {
			
			jsonObject.put("memberId", member.getMemberId());
			jsonObject.put("articleId", articleId);
			ArticleReview articleReview = articleService.selectArticleReviewId(jsonObject.toString());
			
			jsonObject.put("articleReviewId",
					articleReview!=null?
					articleReview.getArticleReviewId():
						null);
			
			if (articleReview == null) {
				model.addAttribute("review", null);
			} else {
				Boolean review = articleService.selectArticleReview(jsonObject.toString());
				model.addAttribute("review", review);
			}
			
//			System.out.println(articleReview.getArticleReview());
			
			if (member.getMemberId()==article.getFkMemberId()) {
//				同一人才能修改刪除
				model.addAttribute("author", true);
			}
		}
		
		return "shoehorn/article";
		
	}
	
//	進入修改文章頁面---------------------------------------------
	
	@GetMapping("/editArticle/{articleId}")
	public String editArticle(@PathVariable(value = "articleId")Integer articleId,
			Model model) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("articleId", articleId);
		
		List<Article> list = articleService.dynamicSelect(jsonObject.toString());
		
		Article article = list.get(0);
		
		model.addAttribute("article", article);
		
		return "shoehorn/editArticle";
	}
	
//	修改文章---------------------------------------------
	@PostMapping("/edit")
	public String edit(
			@RequestParam(name = "articleId",required = false) Integer articleId,
			@RequestParam(name = "poster",required = false) Integer poster,
			@RequestParam(name = "product",required = false) Integer product,
			@RequestParam(name = "title",required = false) String title,
			@RequestParam(name = "content",required = false) String content
			) {
		
		System.out.println("_______"+articleId+"     "+content);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("articleId", articleId);
		jsonObject.put("memberId", poster);
		jsonObject.put("productId", product);
		jsonObject.put("title", title);
		jsonObject.put("content", content);
		
		articleService.updateArticle(jsonObject.toString());
		
		return "redirect:/article?articleId="+articleId;
	}
	
//	刪除文章---------------------------------------------
	
	@GetMapping("/deleteArticle/{articleId}")
	public String deleteArticle(@PathVariable(value = "articleId")Integer articleId) {
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("articleId", articleId);
		
		articleService.deleteArticle(jsonObject.toString());
		
		return "redirect:/forum";
	}
	
//	讚、噓-----------------------------------------
	@PostMapping("/article/like")
	@ResponseBody
	public String like(@RequestBody String body) {
		
		JSONObject jsonObject = new JSONObject(body);
		
		System.out.println(jsonObject.isNull("articleReview")?null:jsonObject.getBoolean("articleReview"));
		
		ArticleReview articleReview = articleService.selectArticleReviewId(jsonObject.toString());
		
		Integer articleReviewId =  articleReview!=null?articleReview.getArticleReviewId():null;
		
		jsonObject.put("articleReviewId", articleReviewId);
		
		articleService.updateArticleReview(jsonObject.toString());
		
		
		ArticleReview afterReview = articleService.selectArticleReviewId(jsonObject.toString());
		
		JSONObject response = new JSONObject();
		response.put("review", afterReview.getArticleReview());
		response.put("xxx", "xxx");
		
		return response.toString();
	}
	
//	新增文章留言---------------------------------------------
	
	
	
	


	
	
	
	
	
//	以下為各功能測試---------------------------------------
	
//	-----Article-----
	
//	新增文章
	@ResponseBody
	@PostMapping("/article/createArticle")
	public void createArticle(@RequestBody String body) {
		articleService.createArticle(body);
	}
	
//	修改文章
	@ResponseBody
	@PostMapping("/article/updateArticle")
	public void updateArticle(@RequestBody String body) {
		articleService.updateArticle(body);
	}
	
//	查詢文章
//	傳入jsonString:搜尋條件;	{}:全部文章	/	{"memberId":4}:memberId
	@ResponseBody
	@PostMapping("/article/selectArticle")
	public List<Article> selectArticle(@RequestBody String body) {
		return articleService.selectArticle(body);
	}
	
//	動態多條件查詢
	@ResponseBody
	@PostMapping("/article/dynamicSelect")
	public List<Article> dynamicSelect(@RequestBody String body) {
		return articleService.dynamicSelect(body);
	}
	
//	刪除文章
	@ResponseBody
	@PostMapping("/article/deleteArticle")
	public void deleteArticle(@RequestBody String body) {
		articleService.deleteArticle(body);
	}
	
//	-----ArticleReview-----
	
//	更新文章評價	如果無該筆紀錄就新增
	@ResponseBody
	@PostMapping("/article/updateArticleReview")
	public void createArticleReview(@RequestBody String body) {
		articleService.updateArticleReview(body);
	}
	
//	查詢文章評價
	@ResponseBody
	@PostMapping("/article/selectArticleReview")
	public Boolean selectArticleReview(@RequestBody String body) {
		return articleService.selectArticleReview(body);
	}
	
//	-----ArticleComment-----
	
//	新增文章留言
	@ResponseBody
	@PostMapping("/article/createArticleComment")
	public void createArticleComment(@RequestBody String body) {
		articleService.createArticleComment(body);
	}
	
//	修改文章留言
	@ResponseBody
	@PostMapping("/article/updateArticleComment")
	public void updateArticleComment(@RequestBody String body) {
		articleService.updateArticleComment(body);
	}
	
//	刪除文章留言
	@ResponseBody
	@PostMapping("/article/deleteArticleComment")
	public void deleteArticleComment(@RequestBody String body) {
		articleService.deleteArticleComment(body);
	}
	
//	查詢文章留言
	@ResponseBody
	@PostMapping("/article/selectArticleComment")
	public List<ArticleComment> selectArticleComment(@RequestBody String body) {
		return articleService.selectArticleComment(body);
	}
	
//	-----ArticleCommentReview-----
	
//	更新文章留言評價	如果無該筆紀錄就新增
	@ResponseBody
	@PostMapping("/article/updateArticleCommentReview")
	public void updateArticleCommentReview(@RequestBody String body) {
		articleService.updateArticleCommentReview(body);
	}
	
//	查詢文章留言評價
	@ResponseBody
	@PostMapping("/article/selectArticleCommentReview")
	public Boolean selectArticleCommentReview(@RequestBody String body) {
		return articleService.selectArticleCommentReview(body);
	}
	
//	-----FaviroteArticle-----	
	
//	更新最愛文章
	@ResponseBody
	@PostMapping("/article/updateFaviroteArticle")
	public void updateFaviroteArticle(@RequestBody String body) {
		articleService.updateFaviroteArticle(body);
	}
	
//	查詢最愛文章(以會員搜尋)
	@ResponseBody
	@PostMapping("/article/selectFaviroteArticle")
	public List<FaviroteArticle> selectFaviroteArticle(@RequestBody String body) {
		return articleService.selectFaviroteArticle(body);
	}
	
//	查詢最愛文章(以文章搜尋)
	@ResponseBody
	@PostMapping("/article/selectFaviroteArticleByArticle")
	public List<FaviroteArticle> selectFaviroteArticleByArticle(@RequestBody String body) {
		return articleService.selectFaviroteArticleByArticle(body);
	}
	
//	-----FollowArticle-----
	
//	更新追蹤文章
	@ResponseBody
	@PostMapping("/article/updateFollowArticle")
	public void updateFollowArticle(@RequestBody String body) {
		articleService.updateFollowArticle(body);
	}
	
//	查詢追蹤文章
	@ResponseBody
	@PostMapping("/article/selectFollowArticle")
	public List<FollowArticle> selectFollowArticle(@RequestBody String body) {
		return articleService.selectFollowArticle(body);
	}	

//	查詢追蹤文章(以文章搜尋)
	@ResponseBody
	@PostMapping("/article/selectFollowArticleByArticle")
	public List<FollowArticle> selectFollowArticleByArticle(@RequestBody String body) {
		return articleService.selectFollowArticleByArticle(body);
	}
	
}
