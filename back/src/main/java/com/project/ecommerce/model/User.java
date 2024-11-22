package com.project.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String firstname;

  @Email
  private String email;

  // @JsonIgnore is used to prevent the password from being serialized to JSON
  // in cart and wishlist responses, it's only because we didn't use DTOs, since this is for demonstration purposes
  @JsonIgnore
  private String password;

  private boolean isAdmin;

}
