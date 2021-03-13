package com.greenfoxacademy.mybookshelf.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Review {

  @Id
  @GeneratedValue
  private long id;

  @ManyToOne
  private User reviewer;

  @ManyToOne
  private Book book;

  @Lob
  private String review;
}
