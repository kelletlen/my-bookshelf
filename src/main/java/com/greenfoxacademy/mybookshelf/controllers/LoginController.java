package com.greenfoxacademy.mybookshelf.controllers;

import com.greenfoxacademy.mybookshelf.dtos.LoggedInUserDTO;
import com.greenfoxacademy.mybookshelf.dtos.UserRegistrationDTO;
import com.greenfoxacademy.mybookshelf.models.ResponseError;
import com.greenfoxacademy.mybookshelf.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  final UserService userService;

  public LoginController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(path = "/users/login")
  public ResponseEntity<?> login (@RequestBody UserRegistrationDTO login) {
    if (login.getUsername() == null && login.getPassword() == null) {
      return ResponseEntity.badRequest().body(new ResponseError("username and password must be set"));
    } else if (login.getPassword() == null) {
      return ResponseEntity.badRequest().body(new ResponseError("password must be set"));
    } else if (login.getUsername() == null) {
      return ResponseEntity.badRequest().body(new ResponseError("username must be set"));
    } else if (!userService.existsByUsername(login.getUsername())) {
      return ResponseEntity.badRequest()
          .body(new ResponseError("user does not exists with the given username password combination"));
    } else {
      LoggedInUserDTO loggedInUser = userService.validateUser(login);
      return ResponseEntity.ok(loggedInUser);
    }
  }
}
