package com.greenfoxacademy.mybookshelf.controllers;

import com.greenfoxacademy.mybookshelf.models.Book;
import com.greenfoxacademy.mybookshelf.models.ResponseError;
import com.greenfoxacademy.mybookshelf.models.ResponseState;
import com.greenfoxacademy.mybookshelf.models.User;
import com.greenfoxacademy.mybookshelf.services.BookService;
import com.greenfoxacademy.mybookshelf.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

  final BookService bookService;
  final UserService userService;

  public BookController(BookService bookService, UserService userService) {
    this.bookService = bookService;
    this.userService = userService;
  }

  @PostMapping(path = "/books/add")
  public ResponseEntity<Object> addBook(@RequestBody Book book) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else if (bookService.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
      return ResponseEntity.badRequest().body(new ResponseError("This book is already in the database."));
    } else {
      bookService.addBook(book);
      return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }
  }

  @GetMapping(path = "/books/search/author")
  public ResponseEntity<Object> searchBookByAuthor(@RequestParam String author) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else if (author == null) {
      return ResponseEntity.badRequest().body(new ResponseError("Please provide an author."));
    } else if (bookService.findAllByAuthor(author).isEmpty()) {
      return ResponseEntity.badRequest().body(new ResponseError("There is no book in our database from this author"));
    } else {
      return ResponseEntity.ok(bookService.findAllByAuthor(author));
    }
  }

  @GetMapping(path = "/books/search/title")
  public ResponseEntity<Object> searchBookByTitle(@RequestParam String title) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else if (title == null) {
      return ResponseEntity.badRequest().body(new ResponseError("Please provide a title."));
    } else if (bookService.findAllByTitle(title).isEmpty()) {
      return ResponseEntity.badRequest().body(new ResponseError("There is no book in our database with this title"));
    } else {
      return ResponseEntity.ok(bookService.findAllByTitle(title));
    }
  }

  @GetMapping(path="/books/search/description")
  public ResponseEntity<Object> searchBookByDescription (@RequestParam String keyword) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else if (keyword == null) {
      return ResponseEntity.badRequest().body(new ResponseError("Please provide a keyword."));
    } else if (bookService.findAllByDescription(keyword).isEmpty()) {
      return ResponseEntity.badRequest().body(new ResponseError("There is no book in our database that matches this keyword."));
    } else {
      return ResponseEntity.ok(bookService.findAllByDescription(keyword));
    }
  }

  @PostMapping(path="/wishlist/add/{id}")
  public ResponseEntity<Object> addBookToWishlist (@PathVariable long id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else if (bookService.findById(id) == null) {
      return ResponseEntity.badRequest().body(new ResponseError("There is no book in the database with this id."));
    } else {
      Book book = bookService.findById(id);
      authenticatedUser.addToWishlist(book);
      userService.saveUser(authenticatedUser);
      return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseState("The book is now added to your wishlist."));
    }
  }

  @DeleteMapping(path="/wishlist/delete/{id}")
  public ResponseEntity<Object> deleteBookFromWishlist (@PathVariable long id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    Book book = bookService.findById(id);
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else if (!userService.isBookInUsersWishlist(authenticatedUser.getId(), id)) {
      return ResponseEntity.badRequest().body(new ResponseError("This book is not in your wishlist."));
    } else {
      authenticatedUser.removeFromWishlist(book);
      userService.saveUser(authenticatedUser);
      return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseState("The book is now removed from your wishlist."));
    }
  }
}

