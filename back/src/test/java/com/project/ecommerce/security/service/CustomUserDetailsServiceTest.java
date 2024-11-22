package com.project.ecommerce.security.service;

import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

  @InjectMocks
  private CustomUserDetailsService customUserDetailsService;

  @Mock
  private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testLoadUserByUsername() {
    String email = "test@example.com";
    User user = new User();
    user.setEmail(email);
    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

    UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

    verify(userRepository, times(1)).findByEmail(email);
    assertNotNull(userDetails);
  }

  @Test
  void testLoadUserByUsernameNotFound() {
    String email = "test@example.com";
    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(email));
  }
}