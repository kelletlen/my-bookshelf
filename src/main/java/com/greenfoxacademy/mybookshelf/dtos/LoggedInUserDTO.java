package com.greenfoxacademy.mybookshelf.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoggedInUserDTO {
  private String username;
  private String accessToken;
}
