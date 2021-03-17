package com.greenfoxacademy.mybookshelf.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.mybookshelf.models.Book;
import com.greenfoxacademy.mybookshelf.models.User;
import com.greenfoxacademy.mybookshelf.repositories.BookRepository;
import com.greenfoxacademy.mybookshelf.repositories.UserRepository;
import com.greenfoxacademy.mybookshelf.services.JwtService;
import com.greenfoxacademy.mybookshelf.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  UserRepository userRepository;

  @Autowired
  BookRepository bookRepository;

  @Autowired
  JwtService jwtService;


  @Test
  public void addBookToWishlistWithIncorrectIdShouldReturnWithBadRequest () throws Exception {
    User newUser = User.builder()
        .username("username")
        .password("password")
        .roles(new HashSet<>())
        .build();
    userRepository.save(newUser);
    String token = jwtService.createToken("username");
    mockMvc.perform(post("/wishlist/add/1")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void addBookToWishlistWithCorrectIdShouldReturnWith200 () throws Exception{
    User newUser = User.builder()
        .username("username")
        .password("password")
        .roles(new HashSet<>())
        .build();
    userRepository.save(newUser);
    Book book = new Book();
    book.setId((long) 1);
    book.setTitle("title");
    book.setAuthor("author");
    book.setDescription("description");
    book.setYear(1999);
    bookRepository.save(book);
    String token = jwtService.createToken("username");

    mockMvc.perform(post("/wishlist/add/1")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isCreated());
  }
}
