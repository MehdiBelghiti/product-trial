package com.project.ecommerce.service.impl;

import com.project.ecommerce.security.jwt.JwtUtil;
import com.project.ecommerce.payload.request.LoginRequest;
import com.project.ecommerce.payload.request.SignupRequest;
import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  public void register(SignupRequest request) {
    User user = new User();
    user.setUsername(request.getUsername());
    user.setFirstname(request.getFirstname());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setAdmin(request.getEmail().equals("admin@admin.com")); // Admin check
    userRepository.save(user);
  }

  @Override
  public Boolean existsByEmail(String email) {
    return userRepository.findByEmail(email).isPresent();
  }

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found for email"));
  }
}
