package com.greenfoxacademy.mybookshelf.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {
  private Long borrowerId;
  private long copyId;
}
