package com.project.ecommerce.service;

import com.project.ecommerce.model.Cart;
import com.project.ecommerce.model.WishList;

public interface WishListService {
  WishList getWishListByUser(Long userId);
  void addProductToWishList(Long userId, Long productId, int quantity);
  void removeProductFromWishList(Long userId, Long productId, int quantity);
}
