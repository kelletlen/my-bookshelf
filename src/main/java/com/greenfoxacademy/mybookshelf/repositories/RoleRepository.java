package com.greenfoxacademy.mybookshelf.repositories;

import com.greenfoxacademy.mybookshelf.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
  Role findByName(String name);
}
