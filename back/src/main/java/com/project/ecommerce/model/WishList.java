package com.project.ecommerce.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * the User/WishList/WishListItem entites can be either simplified or expanded depending on the requirements
 * I chose this model which is neither a complicated nor a oversimplified one
 */
@Entity
@Data
public class WishList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  private User user;

  @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<WishListItem> items = new ArrayList<>();
}