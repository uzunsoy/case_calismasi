package com.uzunsoy.repository;

import java.text.NumberFormat;
import java.text.ParsePosition;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.uzunsoy.model.Review;

public class ReviewPredicate {

	private SearchCriteria criteria;

	public ReviewPredicate(SearchCriteria criteria) {
		 this.criteria= criteria;
	}

	public BooleanExpression getPredicate() {
		PathBuilder<Review> entityPath = new PathBuilder<>(Review.class, "review");

		if (isNumeric(criteria.getValue().toString())) {
			NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
			int value = Integer.parseInt(criteria.getValue().toString());
			switch (criteria.getOperation()) {
			case ":":
				return path.eq(value);
			case ">":
				return path.goe(value);
			case "<":
				return path.loe(value);
			}
		} else {
			StringPath path = entityPath.getString(criteria.getKey());
			if (criteria.getOperation().equalsIgnoreCase(":")) {
				return path.containsIgnoreCase(criteria.getValue().toString());
			}
		}
		return null;
	}

	public static boolean isNumeric(String str) {
		ParsePosition pos = new ParsePosition(0);
		NumberFormat.getInstance().parse(str, pos);
		return str.length() == pos.getIndex();
	}
}