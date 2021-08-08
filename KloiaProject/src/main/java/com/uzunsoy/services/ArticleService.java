package com.uzunsoy.services;

import java.util.Optional;

import com.querydsl.core.types.Predicate;
import com.uzunsoy.model.Article;

public interface ArticleService {

	public Article create(Article nesne);

	public Optional<Article> read(Long nesneId);

	public Article update(Article nesne);

	public Article update(Article nesne, Long Id);

	public void delete(Article nesne);

	public void delete(Long nesneId);

	public Iterable<Article> findAll(Predicate predicate);

	public Article findArticleByIdDynamic(Long id);
	
	
}
