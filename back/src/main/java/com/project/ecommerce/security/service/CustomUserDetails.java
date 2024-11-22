package com.project.ecommerce.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ecommerce.model.User;
import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

  @Serial
  private static final long serialVersionUID = 1L;

  @Getter
  private Long id;


  @Getter
  private String email;


  @JsonIgnore
  private String password;

  public CustomUserDetails(Long id, String email, String password) {
    this.id = id;
    this.email = email;
    this.password = password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // Pas de rôles pour ce projet, on retourne une liste vide
    return Collections.emptyList();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    CustomUserDetails user = (CustomUserDetails) o;
    return Objects.equals(id, user.id);
  }
}
