package com.example.jwtspringsecurity.controllers;

import com.example.jwtspringsecurity.MyUserDetailsService;
import com.example.jwtspringsecurity.models.dtos.AuthenticationRequest;
import com.example.jwtspringsecurity.models.dtos.AuthenticationResponse;
import com.example.jwtspringsecurity.models.dtos.UserDto;
import com.example.jwtspringsecurity.repositories.UserRepository;
import com.example.jwtspringsecurity.services.UserService;
import com.example.jwtspringsecurity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloController {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtUtil jwtTokenUtil;
  @Autowired
  private MyUserDetailsService userDetailsService;
  @Autowired
  private UserService userService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  public HelloController(AuthenticationManager authenticationManager, JwtUtil jwtTokenUtil,
                         MyUserDetailsService userDetailsService, UserService userService,
                         PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userDetailsService = userDetailsService;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping({"/hello"})
  public String firstPage() {
    return "Hello World";
  }

  @GetMapping({"/login"})
  public String loginPage() {
    return "This should be a public login page";
  }

  @PostMapping(value = "/authenticate")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody
                                                     AuthenticationRequest authenticationRequest)
      throws Exception {

    try {

      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

      System.out.println(encoder.matches(authenticationRequest.getPassword(),
          "$2a$10$pHlqd393Q/uTwgV10I54zuj1HCXuggjnXzLQuu.6g7HhrlgKjD6Vy"));

      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authenticationRequest.getUsername(),
              authenticationRequest.getPassword())
      );
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect credentials provided");
    }

    final UserDetails userDetails = userDetailsService
        .loadUserByUsername(authenticationRequest.getUsername());

    final String jwt = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }

  //todo if things go wrong
  @PostMapping("/newuser")
  public ResponseEntity<?> addNewUser(@RequestBody UserDto userDto) throws Exception {
    return ResponseEntity.ok().body(userService.registerNewUserAccount(userDto));
  }
}
