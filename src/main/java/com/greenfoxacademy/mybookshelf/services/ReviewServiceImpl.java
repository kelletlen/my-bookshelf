package com.greenfoxacademy.mybookshelf.services;

import com.greenfoxacademy.mybookshelf.models.Review;
import com.greenfoxacademy.mybookshelf.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService{

  private final ReviewRepository reviewRepository;

  public ReviewServiceImpl(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  @Override
  public Review save(Review review) {
    return reviewRepository.save(review);
  }

  @Override
  public Review findByIdAnd(long id) {
    return reviewRepository.findById(id);
  }

  @Override
  public void deleteById(long id) {
    reviewRepository.deleteById(id);
  }
}
