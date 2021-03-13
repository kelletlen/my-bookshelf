package com.greenfoxacademy.mybookshelf.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.mybookshelf.dtos.LoggedInUserDTO;
import com.greenfoxacademy.mybookshelf.dtos.UserRegistrationDTO;
import com.greenfoxacademy.mybookshelf.models.User;
import com.greenfoxacademy.mybookshelf.services.JwtService;
import com.greenfoxacademy.mybookshelf.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
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

  @Mock
  UserService userService;

  @Mock
  JwtService jwtServiceMock;

  @Test
  public void findUserByUsername () {
    User newUser = User.builder()
        .username("username")
        .password("pw")
        .build();
    String username = newUser.getUsername();
    when(userService.findByUsername(username)).thenReturn(newUser);
    assertEquals(userService.findByUsername(username), newUser);
  }

  @Test
  public void existByUsername() {
    User user = User.builder()
        .username("username")
        .build();
    when(userService.existsByUsername("username")).thenReturn(true);
    assertTrue(userService.existsByUsername("username"));
    assertFalse(userService.existsByUsername(""));
  }

  @Test
  public void validateUser() {
    UserRegistrationDTO user = new UserRegistrationDTO();
    LoggedInUserDTO storedUser = new LoggedInUserDTO();
    storedUser.setUsername(user.getUsername());
    when(userService.validateUser(user)).thenReturn(storedUser);
    assertEquals(userService.validateUser(user), storedUser);
  }

  @Test
  public void noUsernameAndPasswordAdded () throws Exception {
    User newUser = User.builder()
        .username(null)
        .password(null)
        .build();
    mockMvc.perform(post("/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("username and password must be set")));
  }
  @Test
  public void noPasswordAdded() throws Exception {
    User newUser = User.builder()
        .username("username")
        .password(null)
        .build();
    mockMvc.perform(post("/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("password must be set")));
  }
  @Test
  public void noUsernameAdded() throws Exception {
    User newUser = User.builder()
        .username(null)
        .password("pw")
        .build();
    mockMvc.perform(post("/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("username must be set")));
  }

  @Test
  public void userDoesNotExist() throws Exception {
    User newUser = User.builder()
        .password("pw")
        .username("username")
        .build();
    userService.saveUser(newUser);
    User notExistingUser = User.builder()
        .password("pw")
        .username("notExistingUsername")
        .build();
    mockMvc.perform(post("/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(notExistingUser)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("user does not exists with the given username password combination")));
  }
  @Test
  public void success() throws Exception {
    User newUser = User.builder()
        .password("pw")
        .username("username")
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
