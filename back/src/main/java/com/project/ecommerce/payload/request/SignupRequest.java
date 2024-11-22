package com.project.ecommerce.payload.request;

import lombok.Data;

@Data
public class SignupRequest {
  private String username;
  private String firstname;
  private String email;
  private String password;
}
