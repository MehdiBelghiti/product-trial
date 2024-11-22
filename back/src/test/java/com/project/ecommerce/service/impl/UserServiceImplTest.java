package com.project.ecommerce.service.impl;

import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.User;
import com.project.ecommerce.payload.request.SignupRequest;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void existsByEmailTest() {
    String email = "abc@gddm.com";
    User user = new User();
    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

    boolean foundUser = userService.existsByEmail(email);

    verify(userRepository, times(1)).findByEmail(email);
    assertEquals(foundUser, true);
  }

  @Test
  void testSaveUser() {
    User user = new User();
    user.setAdmin(false);
    user.setEmail("abc@avx.com");
    user.setUsername("usr");
    user.setPassword("encodedPassword");
    user.setFirstname("first");
    when(userRepository.save(user)).thenReturn(user);
    when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");


    SignupRequest signupRequest = new SignupRequest();
    signupRequest.setEmail("abc@avx.com");
    signupRequest.setPassword("123456");
    signupRequest.setUsername("usr");
    signupRequest.setFirstname("first");
    userService.register(signupRequest);

    verify(userRepository, times(1)).save(user);
  }
}