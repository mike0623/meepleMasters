package tw.com.eeit162.meepleMasters.shoehorn.forum.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.shoehorn.forum.model.Article;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ArticleDao;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleDao articleDao;
	
	public Article insertArticle() {
		Article article1 = new Article();
		article1.setArticleId(1);
		article1.setFkProductId(1);
		article1.setArticleComment(1);
		
		article1.setArticleContent(1);
		article1.setArticleCreatedDate();
		article1.setArticleUpdatedDate(1);
		
		article1.setArticleTitle("test");
		article1.setFkMemberId(1);

		
		
	}
}
