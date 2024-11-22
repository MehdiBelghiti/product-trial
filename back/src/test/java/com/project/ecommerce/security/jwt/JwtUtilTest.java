package com.project.ecommerce.security.jwt;

import com.project.ecommerce.model.User;
import com.project.ecommerce.security.service.CustomUserDetails;
import com.project.ecommerce.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.lang.reflect.Field;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilTest {

  @InjectMocks
  private JwtUtil jwtUtil;

  @Mock
  private Authentication authentication;

  @Mock
  private CustomUserDetails userPrincipal;

  @Mock
  private UserService userService;

  private String jwtSecret = "mySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKey";
  private long jwtExpirationMs = 3600000;

  @BeforeEach
  @SneakyThrows
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // Use reflection to set the jwtSecret and jwtExpirationMs values
    Field jwtSecretField = JwtUtil.class.getDeclaredField("jwtSecret");
    jwtSecretField.setAccessible(true);
    jwtSecretField.set(jwtUtil, jwtSecret);

    Field jwtExpirationMsField = JwtUtil.class.getDeclaredField("jwtExpirationMs");
    jwtExpirationMsField.setAccessible(true);
    jwtExpirationMsField.set(jwtUtil, jwtExpirationMs);

  }

  @Test
  void testGenerateJwtToken() {
    User user = new User();
    user.setId(1L);
    user.setEmail("test@example.com");
    user.setFirstname("test");
    user.setUsername("user");
    user.setAdmin(false);

    when(authentication.getPrincipal()).thenReturn(userPrincipal);
    when(userPrincipal.getUsername()).thenReturn("user");
    when(authentication.getPrincipal()).thenReturn(userPrincipal);
    when(userPrincipal.getUsername()).thenReturn("test@example.com");
    when(userService.findByEmail(any(String.class))).thenReturn(user);
    when(userPrincipal.getId()).thenReturn(1L);

    String token = jwtUtil.generateJwtToken(authentication);

    Claims claims = Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
        .build()
        .parseClaimsJws(token)
        .getBody();

    assertEquals("test@example.com", claims.getSubject());
  }

  @Test
  void testGetEmailFromJwtToken() {
    String token = Jwts.builder()
        .setSubject("test@example.com")
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
        .compact();

    String email = jwtUtil.getEmailFromJwtToken(token);

    assertEquals("test@example.com", email);
  }

  @Test
  void testValidateJwtToken() {
    String token = Jwts.builder()
        .setSubject("test@example.com")
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
        .compact();

    assertTrue(jwtUtil.validateJwtToken(token));
  }
}