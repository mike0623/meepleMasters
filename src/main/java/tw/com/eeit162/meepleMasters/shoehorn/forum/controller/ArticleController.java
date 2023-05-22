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
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.Article;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleComment;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleCommentReview;
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
		List<String> memberNameList = new ArrayList<String>();
		List<String> productNameList = new ArrayList<String>();
//		List<String> memberList = new ArrayList<String>();
//		List<String> productList = new ArrayList<String>();
		
		List<Member> allMember = articleService.getAllMemberName();
		List<Product> allProduct = articleService.getAllProductName();
		
		
		for(Article article:dynamicSelect) {
			Integer goodCount = articleService.selectArticleGoodReviewCount(article.getArticleId());
			goodReviewCount.add(goodCount);
			Integer badCount = articleService.selectArticleBadReviewCount(article.getArticleId());
			badReviewCount.add(badCount);
			
			String memberName = articleService.getMemberName(article.getFkMemberId());
			memberNameList.add(memberName);
			String productName = articleService.getProductName(article.getFkProductId());
			productNameList.add(productName);
		}
		
		model.addAttribute("articleList",dynamicSelect);
		model.addAttribute("goodReviewCount",goodReviewCount);
		model.addAttribute("badReviewCount",badReviewCount);
		model.addAttribute("memberNameList",memberNameList);
		model.addAttribute("productNameList",productNameList);
		model.addAttribute("allMember",allMember);
		model.addAttribute("allProduct",allProduct);
		
		return "shoehorn/forum";
	}
			
//	多條件查詢後顯示文章
	
	@PostMapping("/forum")
	public String forumIndexPage(
//			@RequestBody(required = false) String body,
			@RequestParam(name = "poster",required = false) String poster,
			@RequestParam(name = "product",required = false) Integer product,
			@RequestParam(name = "title",required = false) String title,
			Model model) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("memberId", articleService.getInputMemberId(poster));
		System.out.println("memberId: "+articleService.getInputMemberId(poster));
		jsonObject.put("productId", product);
		jsonObject.put("articleTitle", title);

		List<Integer> goodReviewCount = new ArrayList<Integer>();
		List<Integer> badReviewCount = new ArrayList<Integer>();
		List<Article> dynamicSelect = articleService.dynamicSelect(jsonObject.toString());
		List<String> memberNameList = new ArrayList<String>();
		List<String> productNameList = new ArrayList<String>();
		List<Member> allMember = articleService.getAllMemberName();
		List<Product> allProduct = articleService.getAllProductName();
		
		for(Article article:dynamicSelect) {
			Integer goodCount = articleService.selectArticleGoodReviewCount(article.getArticleId());
			goodReviewCount.add(goodCount);
			Integer badCount = articleService.selectArticleBadReviewCount(article.getArticleId());
			badReviewCount.add(badCount);
			
			String memberName = articleService.getMemberName(article.getFkMemberId());
			memberNameList.add(memberName);
			String productName = articleService.getProductName(article.getFkProductId());
			productNameList.add(productName);
		}
		model.addAttribute("articleList",dynamicSelect);
		model.addAttribute("goodReviewCount",goodReviewCount);
		model.addAttribute("badReviewCount",badReviewCount);
		model.addAttribute("memberNameList",memberNameList);
		model.addAttribute("productNameList",productNameList);
		model.addAttribute("allMember",allMember);
		model.addAttribute("allProduct",allProduct);
		
		return "shoehorn/forum";
	}
	
//	起始新增文章頁面------------------------------------------
	
	@GetMapping("/newArticle")
	public String newArticle(Model model) {
		List<Product> allProduct = articleService.getAllProductName();
		model.addAttribute("allProduct",allProduct);
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
		//---------------------------------
//		List<comment> articleComments ;
//		List<Dto> dtoList= new List<Dto>();
//		for(Comment comment:articleComments) {
//			Dto dto = new Dto();
//			dto.setCommentId(comment.getId);
//			dto.setCommentId(comment.getarticleId);
//			dto.setCommentId(comment.memberId);
//			MemberDao member = MemberDao.findById(memberId);
//			dto.setCommentId(member.getName);
//			dtoList.add(dto);
//		}
//		model.addAttribute("dtoList", dtoList);
//		JSONObject.put("dtoList", dtoList);
		//---------------------------------
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("articleId", articleId);
		
		List<Article> list = articleService.dynamicSelect(jsonObject.toString());
		
		Article article = list.get(0);
		
		model.addAttribute("article", article);
		
		String memberName = articleService.getMemberName(article.getFkMemberId());
		String productName = articleService.getProductName(article.getFkProductId());
		
		model.addAttribute("memberName", memberName);
		model.addAttribute("productName", productName);
		
		
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
			
			
			if (member.getMemberId()==article.getFkMemberId()) {
//				同一人才能修改刪除
				model.addAttribute("author", true);
			}
		}
		
		
//		取得該文章的留言
		List<ArticleComment> articleCommentList = articleService.selectArticleComment(new JSONObject().put("articleId", articleId).toString());
		
//		System.out.println(articleCommentList.size());
		
		model.addAttribute("articleCommentList",articleCommentList);
		
//		取得文章留言的讚、噓總數
		List<Integer> goodReviewCount = new ArrayList<Integer>();
		List<Integer> badReviewCount = new ArrayList<Integer>();
		List<Boolean> cReview = new ArrayList<Boolean>();
		List<String> memberNameList = new ArrayList<String>();

		
		for(ArticleComment articleComment:articleCommentList) {
			Integer goodCount = articleService.selectArticleCommentGoodReviewCount(articleComment.getArticleCommentId());
			goodReviewCount.add(goodCount);
			Integer badCount = articleService.selectArticleCommentBadReviewCount(articleComment.getArticleCommentId());
			badReviewCount.add(badCount);
			
			String mName = articleService.getMemberName(articleComment.getFkMemberId());
			memberNameList.add(mName);
			
			if (member!=null) {
				ArticleCommentReview selectArticleCommentReviewByMemberId = articleService.selectArticleCommentReviewByMemberId(member.getMemberId(),articleComment.getArticleCommentId());
				
				cReview.add(selectArticleCommentReviewByMemberId!=null?selectArticleCommentReviewByMemberId.getCommentReview():null);
			}
		}

		
		model.addAttribute("goodReviewCount",goodReviewCount);
		model.addAttribute("badReviewCount",badReviewCount);
		model.addAttribute("creview",cReview);
		model.addAttribute("memberNameList",memberNameList);
		
//		取得文章留言的review

		
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
		
		String productName = articleService.getProductName(article.getFkProductId());
		
		model.addAttribute("article", article);
		model.addAttribute("productName", productName);
		
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
	
//	文章 讚、噓-----------------------------------------
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
	
//	文章留言 讚、噓-----------------------------------------
	@PostMapping("/articleComment/like")
	@ResponseBody
	public String clike(@RequestBody String body) {
		
		JSONObject jsonObject = new JSONObject(body);
		
		ArticleCommentReview commentReview = articleService.selectArticleCommentReviewByMemberId(jsonObject.getInt("memberId"), jsonObject.getInt("articleCommentId"));
		
		

		Integer articleCommentReviewId = commentReview!=null?commentReview.getArticleCommentReviewId():null;
		
		jsonObject.put("articleCommentReviewId", articleCommentReviewId);
		
		articleService.updateArticleCommentReview(jsonObject.toString());
		
		ArticleCommentReview creview = articleService.selectArticleCommentReviewByMemberId(jsonObject.getInt("memberId"), jsonObject.getInt("articleCommentId"));
		
		JSONObject response = new JSONObject();
		response.put("creview", creview.getCommentReview());
		response.put("xxx", "xxx");
		
		return response.toString();
	}
	
	
//	新增文章留言---------------------------------------------
	
	@PostMapping("/newArticleComment")
	public String newArticleComment(
		@RequestParam(name = "articleId",required = false) Integer articleId,
		@RequestParam(name = "poster",required = false) Integer poster,
		@RequestParam(name = "product",required = false) Integer product,
		@RequestParam(name = "content",required = false) String content) {
	
	JSONObject jsonObject = new JSONObject();
	jsonObject.put("memberId", poster);
	jsonObject.put("articleId", articleId);
	jsonObject.put("productId", product);
	jsonObject.put("content", content);

	articleService.createArticleComment(jsonObject.toString());
	
	return "redirect:/article?articleId="+articleId;
}
	
	
//	進入修改文章留言頁面---------------------------------------------
	
	@GetMapping("/articleComment")
	public String articleComment(@RequestParam(value = "articleCommentId")Integer articleCommentId,
			Model model) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("articleCommentId", articleCommentId);
		
		List<ArticleComment> list = articleService.selectArticleComment(jsonObject.toString());
		
		ArticleComment articleComment = list.get(0);
		
		model.addAttribute("articleComment", articleComment);
		
		return "shoehorn/editArticleComment";
	}
	
	
//	修改文章留言
	@PostMapping("/editComment")
	public String editComment(
			@RequestParam(name = "articleCommentId",required = false) Integer articleCommentId,
			@RequestParam(name = "content",required = false) String content
			) {
		
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("articleCommentId", articleCommentId);
		jsonObject.put("content", content);
		
		articleService.updateArticleComment(jsonObject.toString());
		
		return "redirect:/article?articleId="+articleService.selectArticleComment(jsonObject.toString()).get(0).getFkArticleId();
	}

//	刪除文章留言
	
	@GetMapping("/deleteComment/{articleCommentId}")
	public String deleteComment(@PathVariable(value = "articleCommentId")Integer articleCommentId) {
		
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("articleCommentId", articleCommentId);
		
		List<ArticleComment> articleComment = articleService.selectArticleComment(jsonObject.toString());
		
		Integer articleId = articleComment.get(0).getFkArticleId();
		
		articleService.deleteArticleComment(jsonObject.toString());
		
		return "redirect:/article?articleId="+articleId;
	}
	
	
	
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
