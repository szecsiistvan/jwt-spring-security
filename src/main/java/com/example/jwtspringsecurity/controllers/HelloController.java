package com.example.jwtspringsecurity.controllers;

import com.example.jwtspringsecurity.MyUserDetailsService;
import com.example.jwtspringsecurity.models.dtos.AuthenticationRequest;
import com.example.jwtspringsecurity.models.dtos.AuthenticationResponse;
import com.example.jwtspringsecurity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

  @GetMapping({ "/hello" })
  public String firstPage() {
    return "Hello World";
  }

  @GetMapping({ "/login" })
  public String loginPage() {
    return "This should be a public login page";
  }

  @PostMapping(value = "/authenticate")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody
                                                     AuthenticationRequest authenticationRequest) throws Exception {

    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
      );
    }
    catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect credentials provided");
    }

    final UserDetails userDetails = userDetailsService
        .loadUserByUsername(authenticationRequest.getUsername());

    final String jwt = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }
}
