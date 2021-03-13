package com.greenfoxacademy.mybookshelf.services;

import com.greenfoxacademy.mybookshelf.models.Review;

public interface ReviewService {
  Review save (Review review);
  Review findByIdAnd (long id);
  void deleteById (long id);
}
