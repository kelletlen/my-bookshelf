package com.greenfoxacademy.mybookshelf.models;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column
  private String username;

  @Column
  private String password;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Copy> bookShelf = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Fetch(value = FetchMode.SUBSELECT)
  private List<Book> wishlist = new ArrayList<>();

  @ManyToMany
  private Set<User> friends = new HashSet<>();

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Fetch(value = FetchMode.SUBSELECT)
  private Set<Role> roles = new HashSet<>();

  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<Role> roles = this.getRoles();
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    for (Role role : roles) {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    }
    return authorities;
  }

  public void addToBookShelf (Copy copy) {
    this.bookShelf.add(copy);
  }

  public void addToRoles(Role newRole) {
    this.roles.add(newRole);
  }

  public void addToWishlist (Book book) {
    this.wishlist.add(book);
  }

  public void removeFromWishlist (Book book) {
    this.wishlist.remove(book);
  }
}
