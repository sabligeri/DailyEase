package com.codecool.men.security.service;


import com.codecool.men.repository.model.RoleEntity;
import com.codecool.men.repository.model.UserEntity;
import com.codecool.men.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username)
          throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

    List<SimpleGrantedAuthority> roles = new ArrayList<>();
    for (RoleEntity role : userEntity.getRoles()) {
      System.out.println("role: " + role.getRole().name());
      roles.add(new SimpleGrantedAuthority(role.getRole().name()));
    }

    return new User(userEntity.getUsername(), userEntity.getPassword(), roles);
  }
}

