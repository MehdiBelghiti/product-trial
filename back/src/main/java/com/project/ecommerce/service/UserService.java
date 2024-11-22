package com.project.ecommerce.service;

import com.project.ecommerce.model.User;
import com.project.ecommerce.payload.request.LoginRequest;
import com.project.ecommerce.payload.request.SignupRequest;

public interface UserService {

  void register(SignupRequest request);

  Boolean existsByEmail(String email);

  User findByEmail(String email);

}
