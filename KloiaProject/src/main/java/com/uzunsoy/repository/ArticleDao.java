package com.uzunsoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.google.common.base.Optional;
import com.querydsl.core.types.dsl.StringPath;
import com.uzunsoy.model.Article;
import com.uzunsoy.model.QArticle;

 
public interface ArticleDao extends JpaRepository<Article, Long>,QuerydslPredicateExecutor<Article> ,QuerydslBinderCustomizer<QArticle> {

	@Override
	default void customize(QuerydslBindings bindings, QArticle root) {
		
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
		
	}

	@Query("select a from Article a left join fetch a.reviews where a.artId = ?1")
	public  Article  findArticleById(Long artId);
	
	
}
