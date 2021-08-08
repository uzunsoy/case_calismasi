package com.uzunsoy.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

public class ReviewPredicatesBuilder {
	private List<SearchCriteria> params;

	public ReviewPredicatesBuilder() {
        params = new ArrayList<>();
    }

	public ReviewPredicatesBuilder with(String key, String operation, Object value) {

		params.add(new SearchCriteria(key, operation, value));
		return this;
	}

	public BooleanExpression build() {
		if (params.size() == 0) {
			return null;
		}

		List<BooleanExpression> predicates = params.stream().map(param -> {
			ReviewPredicate predicate = new ReviewPredicate(param);
			return predicate.getPredicate();
		}).filter(Objects::nonNull).collect(Collectors.toList());

		BooleanExpression result = Expressions.asBoolean(true).isTrue();
		for (BooleanExpression predicate : predicates) {
			result = result.and(predicate);
		}
		return result;
	}
}