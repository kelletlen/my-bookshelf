package com.greenfoxacademy.mybookshelf.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.mybookshelf.models.User;
import com.greenfoxacademy.mybookshelf.repositories.UserRepository;
import com.greenfoxacademy.mybookshelf.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import java.util.HashSet;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginControllerTest {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  UserRepository userRepository;

  @BeforeEach
  void deleteDB() {
    userRepository.deleteAll();
  }


  @Test
  public void loginWithoutUsernameAndPasswordShouldReturnWithBadRequest () throws Exception {
    User newUser = User.builder()
        .username(null)
        .password(null)
        .roles(new HashSet<>())
        .build();
    mockMvc.perform(post("/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("username and password must be set")));
  }

  @Test
  public void loginWithoutPasswordShouldReturnWithBadRequest() throws Exception {
    User newUser = User.builder()
        .username("username")
        .password(null)
        .roles(new HashSet<>())
        .build();
    mockMvc.perform(post("/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("password must be set")));
  }
  @Test
  public void loginWithoutUsernameShouldReturnWithBadRequest() throws Exception {
    User newUser = User.builder()
        .username(null)
        .password("pw")
        .roles(new HashSet<>())
        .build();
    mockMvc.perform(post("/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("username must be set")));
  }

  @Test
  public void loginWithNonExistingUsernameShouldReturnBadRequest() throws Exception {
    User newUser = User.builder()
        .password("pw")
        .username("username")
        .roles(new HashSet<>())
        .build();
    userRepository.save(newUser);
    User notExistingUser = User.builder()
        .password("pw")
        .username("notExistingUsername")
        .roles(new HashSet<>())
        .build();
    mockMvc.perform(post("/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(notExistingUser)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("user does not exists with the given username password combination")));
  }
  @Test
  public void loginWithCorrectUsernameAndPasswordShouldReturnToken() throws Exception {
    User newUser = User.builder()
        .password("pw")
        .username("username")
        .roles(new HashSet<>())
        .build();
    mockMvc.perform(post("/users/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isOk());
    mockMvc.perform(post("/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", is("username")))
        .andExpect(jsonPath("$.accessToken").exists());
  }
}
