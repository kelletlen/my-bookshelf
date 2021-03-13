package com.greenfoxacademy.mybookshelf.repositories;

import com.greenfoxacademy.mybookshelf.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
  boolean existsByTitleAndAuthor(String title, String author);
  List<Book> findAllByAuthorContainingIgnoreCase (String string);
  List<Book> findAllByTitleContainingIgnoreCase (String string);
  List<Book> findAllByDescriptionContainingIgnoreCase (String string);
  Book findById (long id);
}
