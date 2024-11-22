package com.project.ecommerce.controller;

import com.project.ecommerce.model.Cart;
import com.project.ecommerce.payload.response.GenericResponse;
import com.project.ecommerce.service.CartService;
import com.project.ecommerce.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

  @Autowired
  private WishListService wishListService;

  @GetMapping("/{userId}")
  public ResponseEntity<?> getWishList(@PathVariable Long userId) {
    return ResponseEntity.ok(wishListService.getWishListByUser(userId));
  }

  @PostMapping("/add/{userId}/{productId}")
  public ResponseEntity<GenericResponse> addToWishList(@PathVariable Long userId, @PathVariable Long productId, @RequestParam int quantity) {
    wishListService.addProductToWishList(userId, productId, quantity);
    return ResponseEntity.ok(new GenericResponse("Product added to wish list successfully"));
  }

  @PostMapping("/reduce/{userId}/{productId}")
  public ResponseEntity<GenericResponse> removeFromWishList(@PathVariable Long userId, @PathVariable Long productId, @RequestParam int quantity) {
    wishListService.removeProductFromWishList(userId, productId, quantity);
    return ResponseEntity.ok(new GenericResponse("Product removed from wish list successfully"));
  }
}
