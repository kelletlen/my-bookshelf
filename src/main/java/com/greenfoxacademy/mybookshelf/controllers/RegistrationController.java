package com.greenfoxacademy.mybookshelf.controllers;

import com.greenfoxacademy.mybookshelf.dtos.UserRegistrationDTO;
import com.greenfoxacademy.mybookshelf.models.ResponseError;
import com.greenfoxacademy.mybookshelf.models.ResponseState;
import com.greenfoxacademy.mybookshelf.models.User;
import com.greenfoxacademy.mybookshelf.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

  final UserService userService;
  final BCryptPasswordEncoder bCryptPasswordEncoder;

  public RegistrationController(UserService userService, BCryptPasswordEncoder encoder) {
    this.userService = userService;
    this.bCryptPasswordEncoder = encoder;
  }

  @PostMapping(value="/users/register")
  public ResponseEntity<?> register (@RequestBody UserRegistrationDTO newUser) {
    if (newUser.getUsername() == null && newUser.getPassword() == null) {
      return ResponseEntity.badRequest().body(new ResponseError("username and password cannot be empty"));
    } else if (newUser.getPassword() == null) {
      return ResponseEntity.badRequest().body(new ResponseError("password cannot be empty"));
    } else if (newUser.getUsername() == null) {
      return ResponseEntity.badRequest().body(new ResponseError("username cannot be empty"));
    } else if (userService.existsByUsername(newUser.getUsername())) {
      return ResponseEntity.badRequest().body(new ResponseError("username already exists"));
    } else {
      userService.saveAndHashPassword(newUser.getUsername(), newUser.getPassword());
      return ResponseEntity.ok().body(new ResponseState("success"));
    }
  }
}
