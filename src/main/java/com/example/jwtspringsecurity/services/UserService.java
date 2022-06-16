package com.example.jwtspringsecurity.services;

import com.example.jwtspringsecurity.models.User;
import com.example.jwtspringsecurity.models.dtos.UserDto;
import com.example.jwtspringsecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  public User registerNewUserAccount(UserDto accountDto) throws Exception{

    // TODO: 2022. 06. 15. check if two password matches and username exists

    User user = new User();
    user.setUserName(accountDto.getUserName());
//    user.setPassword(accountDto.getPassword());
    user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
    user.setRoles("USER_ROLE");

    return userRepository.save(user);
  }
}
