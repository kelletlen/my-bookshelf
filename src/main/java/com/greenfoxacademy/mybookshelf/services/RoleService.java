package com.greenfoxacademy.mybookshelf.services;

import com.greenfoxacademy.mybookshelf.models.Role;

public interface RoleService {
  Role findByName(String name);

  Role save(Role role);
}
