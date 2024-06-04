package com.codecool.men.service;

import com.codecool.men.controller.dto.user.NewUserDTO;
import com.codecool.men.controller.dto.user.UserNameDTO;
import com.codecool.men.controller.dto.user.UserPasswordDTO;
import com.codecool.men.controller.exceptions.OperationFailedException;
import com.codecool.men.repository.UserRepository;
import com.codecool.men.repository.model.Role;
import com.codecool.men.repository.model.RoleEntity;
import com.codecool.men.repository.model.UserEntity;
import com.codecool.men.repository.model.payload.JwtResponse;
import com.codecool.men.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;
  private final PasswordEncoder encoder;


  @Autowired
  public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
    this.encoder = encoder;
  }

  public ResponseEntity<JwtResponse> loginUser(NewUserDTO newUserDTO) {

    Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(newUserDTO.name(), newUserDTO.password()));


    String jwt = jwtUtils.generateJwtToken(authentication);

    User userDetails = (User) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
            .toList();

    Optional<UserEntity> userEntity = userRepository.findByUsername(newUserDTO.name());
    System.out.println(userEntity);
      long userId = userEntity.get().getId();
      return ResponseEntity
              .ok(new JwtResponse(jwt, userId, userDetails.getUsername(), roles));

  }

  public UserNameDTO editUsername(UserNameDTO usernameDTO, int userId) {
    Optional<UserEntity> user = userRepository.findById((long) userId);
    if (user.isPresent()) {
      user.get().setUsername(usernameDTO.username());
      userRepository.save(user.get());
      return new UserNameDTO(user.get().getUsername());
    }
    throw new OperationFailedException("User not found!");
  }

  public boolean editUserPassword(UserPasswordDTO passwordDTO, long userId) {
    Optional<UserEntity> user = userRepository.findById(userId);
    if (user.isPresent()) {
      user.get().setPassword(passwordDTO.password());
      userRepository.save(user.get());
      return true;
    }
    throw new OperationFailedException("User not found!");
  }
  public boolean addPremiumToUser(long userId){
    Optional<UserEntity> user = userRepository.findById(userId);
    if (user.isPresent()) {
      if(user.get().getRoles().stream().map(roleEntity -> roleEntity.isGivenRole(Role.ROLE_PREMIUM)).anyMatch(Predicate.isEqual(true))){
        throw new OperationFailedException("Already has Premium!");
      }
      RoleEntity role = new RoleEntity();
      role.setRole(Role.ROLE_PREMIUM);
      role.setUser(user.get());
      user.get().addRole(role);
      userRepository.save(user.get());
      return true;
    }
    throw new OperationFailedException("User not found!");
  }

  public boolean deleteUser(long userId) {
    Optional<UserEntity> user = userRepository.findById(userId);
    if (user.isPresent()) {
      userRepository.delete(user.get());
      return true;
    }
    throw new OperationFailedException("User not found!");
  }

  public boolean addUser(NewUserDTO newUserDTO) {
    Optional<UserEntity> user = userRepository.findByUsername(newUserDTO.name());
    if (user.isEmpty()) {
      UserEntity newUser = new UserEntity();
      newUser.setUsername(newUserDTO.name());
      newUser.setPassword(encoder.encode(newUserDTO.password()));
      //newUser.setPassword(newUserDTO.password());
      RoleEntity role = new RoleEntity();
      role.setRole(Role.ROLE_USER);
      role.setUser(newUser);

      newUser.setRoles(Set.of(role));

      userRepository.save(newUser);
      return true;
    } else {
      throw new OperationFailedException("Username already in use!");
    }
  }
}
