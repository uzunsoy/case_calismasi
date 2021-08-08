package com.uzunsoy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.uzunsoy.model.Review;
import com.uzunsoy.repository.ReviewDao;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class ReviewServiceImpl implements ReviewService {

	private ReviewDao reviewDao;

	@Autowired(required = true)
	public ReviewServiceImpl(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}

	@Override
	public Review create(Review nesne) {

		return reviewDao.save(nesne);
	}

	@Override
	public Optional<Review> read(Long nesneId) {

		return reviewDao.findById(nesneId);
	}

	@Override
	public Review update(Review nesne) {

		return reviewDao.save(nesne);
	}

	@Override
	public void delete(Review nesne) {

		reviewDao.delete(nesne);
	}

	@Override
	public Iterable<Review> findAll(Predicate predicate) {

		return reviewDao.findAll(predicate);
	}

	@Override
	public Review update(Review newObject, Long Id) {
		Optional<Review> oldReviewIn = this.read(Id);

		if(oldReviewIn.isPresent()) {
			Review oldReview = oldReviewIn.get();
			if (newObject.getArticle() != null) oldReview.setArticle(newObject.getArticle());
			if (newObject.getReviewContent() != null) oldReview.setReviewContent(newObject.getReviewContent());
	
			return reviewDao.save(oldReview);
			
		}else {
			return null;
		}
	}

	@Override
	public void delete(Long nesneId) {

		reviewDao.deleteById(nesneId);

	}

	@Override
	public Review findById(Long id) {
		 
		Optional<Review> re = reviewDao.findById(id);
		if(re.isPresent()) return re.get();
		return null;
	}
}
