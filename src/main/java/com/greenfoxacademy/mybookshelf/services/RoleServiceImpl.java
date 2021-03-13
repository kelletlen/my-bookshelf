package com.greenfoxacademy.mybookshelf.services;

import com.greenfoxacademy.mybookshelf.models.Role;
import com.greenfoxacademy.mybookshelf.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  final RoleRepository roleRepository;

  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role findByName(String name) {
    return roleRepository.findByName(name);
  }

  @Override
  public Role save(Role role) {
    return roleRepository.save(role);
  }
}
