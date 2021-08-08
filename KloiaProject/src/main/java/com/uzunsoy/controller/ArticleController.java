package com.uzunsoy.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.uzunsoy.model.Article;
import com.uzunsoy.model.QReview;
import com.uzunsoy.services.ArticleService;

@RestController
@RequestMapping("rest")
public class ArticleController {

	private ArticleService articleService;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	/**
	 * PostBody örneği { "title":"Ornek 1", "articleContent":"İçerik 1",
	 * "starCount":6 }
	 * 
	 * @param newArticle
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/articles")
	@ResponseBody
	public Article createArticle(@RequestBody Article newArticle) {

		return articleService.create(newArticle);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/articles")
	@ResponseBody
	public Iterable<Article> findAllArticles(@QuerydslPredicate(root = Article.class) Predicate predicate) {

		return articleService.findAll(predicate);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/articles/{articleId}")
	@ResponseBody
	public Optional<Article> findArticleById(@PathVariable Long articleId) {

		return articleService.read(articleId);

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/articles/{articleId}")
	@ResponseBody
	public Article updateArticle(@RequestBody Article oldArticle, @PathVariable Long articleId) {

		return articleService.update(oldArticle, articleId);

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/articles/{articleId}")
	@ResponseBody
	public Boolean deleteArticle(@PathVariable Long articleId) {

		QReview reviewQuery = QReview.review;
		JPAQuery<QReview> q = new JPAQuery<>(entityManager);
		List<QReview> listReview = q.from(reviewQuery).where(reviewQuery.articleId.eq(articleId)).fetch();

		if (listReview.isEmpty()) {
			articleService.delete(articleId);
			return true;
		} else {
			return false;
		}

	}

}
