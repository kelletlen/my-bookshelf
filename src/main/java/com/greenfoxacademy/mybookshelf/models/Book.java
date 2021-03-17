package com.greenfoxacademy.mybookshelf.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String title;

  @Column
  private String author;

  @Lob
  private String description;

  @Column
  private int year;

  @JsonIgnore
  @OneToMany
  private List<Copy> copies = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return year == book.year &&
        id.equals(book.id) &&
        title.equals(book.title) &&
        author.equals(book.author) &&
        description.equals(book.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, author, description, year, copies);
  }
}
