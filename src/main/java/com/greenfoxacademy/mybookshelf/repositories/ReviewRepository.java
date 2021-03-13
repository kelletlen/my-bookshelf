package com.greenfoxacademy.mybookshelf.repositories;

import com.greenfoxacademy.mybookshelf.models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
 Review findById (long id);
 void deleteById (long id);
}
