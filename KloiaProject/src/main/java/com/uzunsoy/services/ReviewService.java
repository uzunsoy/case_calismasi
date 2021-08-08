package com.uzunsoy.services;

import java.util.Optional;

import com.querydsl.core.types.Predicate;
import com.uzunsoy.model.Review;

public interface ReviewService {

	public Review create(Review nesne);

	public Optional<Review> read(Long nesneId);

	public Review update(Review nesne);

	public Review update(Review nesne, Long Id);

	public void delete(Review nesne);

	public void delete(Long nesneId);

	public Iterable<Review> findAll(Predicate predicate);

	public Review findById(Long id);
	
}
