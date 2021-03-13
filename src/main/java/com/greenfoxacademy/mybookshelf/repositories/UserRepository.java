package com.greenfoxacademy.mybookshelf.repositories;

import com.greenfoxacademy.mybookshelf.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
 boolean existsByUsername(String username);
 User findByUsername(String username);
 User findById(long id);
 boolean existsById(long id);
 void deleteById(long id);
}
