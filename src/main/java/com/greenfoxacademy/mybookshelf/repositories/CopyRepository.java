package com.greenfoxacademy.mybookshelf.repositories;

import com.greenfoxacademy.mybookshelf.models.Copy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopyRepository extends CrudRepository<Copy, Long> {
  Copy findById (long id);
}
