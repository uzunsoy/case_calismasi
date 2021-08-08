package com.uzunsoy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.querydsl.core.types.Predicate;
import com.uzunsoy.model.Article;
import com.uzunsoy.repository.ArticleDao;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class ArticleServiceImpl  implements ArticleService {
 
	 
	
	private ArticleDao articleDao;

	@Autowired(required = true)
	public ArticleServiceImpl(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	@Override
	public Article create(Article nesne) {

		return articleDao.save(nesne);
	}

	@Override
	public Optional<Article> read(Long nesneId) {
 
	   Optional<Article> sonuc = articleDao.findById(nesneId);
	   return sonuc;
	}

	@Override
	public Article update(Article nesne) {

		return articleDao.save(nesne);
	}

	@Override
	public void delete(Article nesne) {
		
		 articleDao.delete(nesne);
	}

	@Override
	public Iterable<Article> findAll(Predicate predicate) {

		return articleDao.findAll(predicate);
	}

	 
	@Override
	public Article update(Article newObject, Long Id) {
		Optional<Article> oldArticleIn = this.read(Id);
		
		if(oldArticleIn.isPresent()) {
			Article oldArticle = oldArticleIn.get();
			if(newObject.getArticleContent()!=null) oldArticle.setArticleContent(newObject.getArticleContent());
			if(newObject.getStarCount()!=null) oldArticle.setStarCount(newObject.getStarCount());
			if(newObject.getTitle()!=null) oldArticle.setTitle(newObject.getTitle());
					
			return articleDao.save(oldArticle);
			
		}else {
			return null;
		}
	}

	@Override
	public void delete(Long nesneId) {
		 
		articleDao.deleteById(nesneId);
		
	}

	@Override
	public Article findArticleByIdDynamic(Long id) {
		 
		return articleDao.findArticleById(id);
	}

}
