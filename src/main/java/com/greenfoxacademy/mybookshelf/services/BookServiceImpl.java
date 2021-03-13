package com.greenfoxacademy.mybookshelf.services;

import com.greenfoxacademy.mybookshelf.models.Book;
import com.greenfoxacademy.mybookshelf.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public Book addBook(Book book) {
    return bookRepository.save(book);
  }

  @Override
  public boolean existsByTitleAndAuthor(String title, String author) {
    return bookRepository.existsByTitleAndAuthor(title, author);
  }

  @Override
  public List<Book> findAllByAuthor(String string) {
    return bookRepository.findAllByAuthorContainingIgnoreCase(string);
  }

  @Override
  public List<Book> findAllByTitle(String string) {
    return bookRepository.findAllByTitleContainingIgnoreCase(string);
  }

  @Override
  public List<Book> findAllByDescription(String string) {
    return bookRepository.findAllByDescriptionContainingIgnoreCase(string);
  }

  @Override
  public Book findById(long id) {
    return bookRepository.findById(id);
  }
}
