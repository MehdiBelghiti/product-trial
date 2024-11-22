package com.project.ecommerce.controller;

import com.project.ecommerce.payload.request.LoginRequest;
import com.project.ecommerce.payload.request.SignupRequest;
import com.project.ecommerce.payload.response.GenericResponse;
import com.project.ecommerce.payload.response.JwtResponse;
import com.project.ecommerce.security.jwt.JwtUtil;
import com.project.ecommerce.security.service.CustomUserDetails;
import com.project.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  private UserService userService;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtil jwtUtil;

  @PostMapping("/account")
  public ResponseEntity<GenericResponse> register(@RequestBody SignupRequest request) {

    if(userService.existsByEmail(request.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new GenericResponse("Error: Email is already taken!"));
    }
    userService.register(request);

    return ResponseEntity.ok(new GenericResponse("User registered successfully!"));
  }

  @PostMapping("/token")
  public ResponseEntity<JwtResponse> authenticate(@Valid @RequestBody LoginRequest request) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtUtil.generateJwtToken(authentication);

      CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

      return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getEmail()));
    } catch (AuthenticationException e) {
      return ResponseEntity
          .badRequest()
          .body(new JwtResponse(null, e.getMessage()));
    }
  }
}