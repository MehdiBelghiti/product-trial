package com.project.ecommerce.controller;

import com.project.ecommerce.payload.request.LoginRequest;
import com.project.ecommerce.payload.response.JwtResponse;
import com.project.ecommerce.security.jwt.JwtUtil;
import com.project.ecommerce.security.service.CustomUserDetails;
import com.project.ecommerce.security.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

  @InjectMocks
  private AuthController authController;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtUtil jwtUtil;

  @Mock
  private CustomUserDetailsService customUserDetailsService;

  @Mock
  private CustomUserDetails customUserDetails;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testAuthenticateUser() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail("test@example.com");
    loginRequest.setPassword("password");

    Authentication authentication = mock(Authentication.class);
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(customUserDetails);
    when(customUserDetails.getEmail()).thenReturn("test@example.com");
    when(jwtUtil.generateJwtToken(authentication)).thenReturn("jwtToken");

    ResponseEntity<JwtResponse> response = authController.authenticate(loginRequest);

    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(jwtUtil, times(1)).generateJwtToken(authentication);
    assertEquals(200, response.getStatusCodeValue());
    assertNotNull(response.getBody());
    assertEquals("jwtToken", response.getBody().getToken());
  }
}