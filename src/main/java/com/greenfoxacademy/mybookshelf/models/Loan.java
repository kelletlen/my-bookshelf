package com.greenfoxacademy.mybookshelf.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Loan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  private User loaner;

  @ManyToOne
  private User borrower;

  @OneToOne
  private Copy copy;
}
