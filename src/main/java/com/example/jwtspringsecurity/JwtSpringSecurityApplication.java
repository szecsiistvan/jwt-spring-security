package com.example.jwtspringsecurity;

import com.example.jwtspringsecurity.models.User;
import com.example.jwtspringsecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtSpringSecurityApplication implements CommandLineRunner {

  @Autowired
  private UserRepository userRepository;

  public JwtSpringSecurityApplication(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(JwtSpringSecurityApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    User user = new User(1L, "pista", "maci", "USER");
    userRepository.save(user);
  }
}
