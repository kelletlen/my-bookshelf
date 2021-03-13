package com.greenfoxacademy.mybookshelf.services;

import com.greenfoxacademy.mybookshelf.models.Book;

import java.util.List;

public interface BookService {
  Book addBook (Book book);
  boolean existsByTitleAndAuthor(String title, String author);
  List<Book> findAllByAuthor (String string);
  List<Book> findAllByTitle (String string);
  List<Book> findAllByDescription (String string);
  Book findById (long id);
}
