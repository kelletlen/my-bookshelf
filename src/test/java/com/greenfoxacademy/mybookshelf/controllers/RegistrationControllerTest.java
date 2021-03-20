package com.greenfoxacademy.mybookshelf.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.mybookshelf.models.User;
import com.greenfoxacademy.mybookshelf.repositories.UserRepository;
import com.greenfoxacademy.mybookshelf.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RegistrationControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @BeforeEach
  void deleteDB() {
    userRepository.deleteAll();
  }


  @Test
  public void registerWithoutUsernameShouldReturnWithBadRequest() throws Exception {
    User newUser = User.builder()
        .username(null)
        .password("pw")
        .roles(new HashSet<>())
        .build();
    mockMvc.perform(post("/users/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("username cannot be empty")));
  }

  @Test
  public void registerWithoutPasswordShouldReturnWithBadRequest() throws Exception {
    User newUser = User.builder()
        .username("user")
        .password(null)
        .roles(new HashSet<>())
        .build();
    mockMvc.perform(post("/users/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("password cannot be empty")));
  }

  @Test
  public void registerWithoutUsernameAndPasswordShouldReturnWithBadRequest() throws Exception {
    User newUser = User.builder()
        .username(null)
        .password(null)
        .roles(new HashSet<>())
        .build();
    mockMvc.perform(post("/users/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("username and password cannot be empty")));
  }

  @Test
  public void registerWithAlreadyExistingUsernameShouldReturnBadRequest() throws Exception {
    User user = User.builder()
        .username("username")
        .password("pw")
        .roles(new HashSet<>())
        .build();
    userService.saveUser(user);
    System.out.println(userService.existsByUsername("username"));
    User newUser = User.builder()
        .username("username")
        .password("password")
        .roles(new HashSet<>())
        .build();
    mockMvc.perform(post("/users/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("username already exists")));

  }

  @Test
  public void registerWithUsernameAndPasswordShouldReturnOk() throws Exception {
    User newUser = User.builder()
        .username("username")
        .password("pw")
        .roles(new HashSet<>())
        .build();
    mockMvc.perform(post("/users/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.state", is("success")));
  }
}
