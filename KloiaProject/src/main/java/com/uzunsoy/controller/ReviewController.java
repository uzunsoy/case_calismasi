package com.uzunsoy.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.uzunsoy.model.Article;
import com.uzunsoy.model.QReview;
import com.uzunsoy.model.Review;
import com.uzunsoy.services.ArticleService;
import com.uzunsoy.services.ReviewService;

@RestController
@RequestMapping("rest")
public class ReviewController {

	private ReviewService reviewService;

	private ArticleService articleService;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired(required = true)
	public ReviewController(ReviewService reviewService,ArticleService articleService) {
		this.reviewService = reviewService;
		this.articleService = articleService;
	}
 
	
	@RequestMapping(method = RequestMethod.POST, value = "/reviews")
	@ResponseBody
	public Review createReview(@RequestBody Review newReview) throws InvalidInputException {
		
		Long artId =  newReview.getArticleId();
		if(artId != null) {		
			Optional<Article> art = articleService.read(artId);
			
			if(art.isPresent()) {
				newReview.setArticle(art.get());
				Review rev = reviewService.create(newReview);
				return rev;
			}else {
				throw new InvalidInputException("ID:"+artId+" ile Article Mevcut Değil!");
			}
		}else {
			throw new InvalidInputException("Article ID set edilmemiş!");
		} 
	}
 
	@RequestMapping(method = RequestMethod.GET, value = "/reviews")
	@ResponseBody
	public List<QReview> findAllReview(
			@RequestParam(required = false) String reviewer, 
			@RequestParam(required = false) String reviewCount,
			@RequestParam(required = false) String articleId,
			@RequestParam(required = false) String revId){
		
		 QReview reviewQuery = QReview.review;
		 JPAQuery<QReview> q = new JPAQuery<>(entityManager);
		 	 
		 BooleanBuilder booleanBuilder = new BooleanBuilder();
		 
		 
		 if(reviewer!=null)  booleanBuilder.and(reviewQuery.reviewer.eq(reviewer));   
		 if(articleId!=null) booleanBuilder.and(reviewQuery.articleId.eq(Long.decode(articleId))); 
		 if(revId!=null)  booleanBuilder.and(reviewQuery.articleId.eq(Long.decode(articleId)));
		 
		 List<QReview> listReview=null;
		 
		 if(reviewCount != null) {
			 listReview = q.from(reviewQuery).where(booleanBuilder).limit(Integer.valueOf(reviewCount)).fetch();
		 }
		 else {
			 listReview = q.from(reviewQuery).where(booleanBuilder).fetch();
		 }
	     return listReview; 
		
	}	
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/reviews/{reviewId}")
	@ResponseBody
	public Optional<Review> findReviewById(@PathVariable Long reviewId) {

		return reviewService.read(reviewId);

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/reviews/{reviewId}")
	@ResponseBody
	public Review updateReview(@RequestBody Review oldReview, @PathVariable Long reviewId) {

		return reviewService.update(oldReview, reviewId);

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/reviews/{reviewId}")
	@ResponseBody
	public Boolean deleteReview(@PathVariable Long reviewId) {

		reviewService.delete(reviewId);
		return true;
	}

}
