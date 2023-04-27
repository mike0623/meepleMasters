package tw.com.eeit162.meepleMasters.shoehorn.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tw.com.eeit162.meepleMasters.shoehorn.forum.service.ArticleService;

@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	
//	新增文章
	@PostMapping("/article/createArticle")
	public void createArticle(@RequestBody String body) {
		articleService.createArticle(body);
	}
	
//	修改文章
	@PostMapping("/article/updateArticle")
	public void updateArticle(@RequestBody String body) {
		articleService.updateArticle(body);
	}
	
//	查詢文章
	@PostMapping("/article/selectArticle")
	public void selectArticle() {
		
	}
	
//	刪除文章
	@PostMapping("/article/deleteArticle")
	public void deleteArticle(@RequestBody String body) {
		articleService.deleteArticle(body);
	}
}
