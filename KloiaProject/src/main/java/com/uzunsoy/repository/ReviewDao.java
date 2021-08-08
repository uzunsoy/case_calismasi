package com.uzunsoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;
import com.uzunsoy.model.QReview;
import com.uzunsoy.model.Review;

public interface ReviewDao extends JpaRepository<Review, Long>, QuerydslPredicateExecutor<Review>, QuerydslBinderCustomizer<QReview> {

	@Override
	default void customize(QuerydslBindings bindings, QReview root) {
		 
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	 
	}

}
