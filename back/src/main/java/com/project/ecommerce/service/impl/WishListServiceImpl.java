package com.project.ecommerce.service.impl;

import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.Cart;
import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.User;
import com.project.ecommerce.model.WishList;
import com.project.ecommerce.model.WishListItem;
import com.project.ecommerce.repository.CartRepository;
import com.project.ecommerce.repository.ProductRepository;
import com.project.ecommerce.repository.WishListRepository;
import com.project.ecommerce.service.CartService;
import com.project.ecommerce.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishListServiceImpl implements WishListService {

  @Autowired
  private WishListRepository wishListRepository;

  @Autowired
  private ProductRepository productRepository;

  @Override
  public WishList getWishListByUser(Long userId) {
    return wishListRepository.findByUserId(userId)
        .orElseThrow(() -> new ResourceNotFoundException("WishList not found for user id: " + userId));
  }

  @Override
  public void addProductToWishList(Long userId, Long productId, int quantity) {
    WishList wishList = wishListRepository.findByUserId(userId)
        .orElseGet(() -> createWishListForUser(userId));

    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

    WishListItem existingWishListItem = wishList.getItems().stream()
        .filter(item -> item.getProduct().getId().equals(productId))
        .findFirst()
        .orElse(null);

    int totalQuantity = quantity;
    if (existingWishListItem != null) {
      totalQuantity += existingWishListItem.getQuantity();
    }

    if (product.getQuantity() < totalQuantity) {
      throw new IllegalArgumentException("Insufficient product quantity available");
    }

    if (existingWishListItem != null) {
      existingWishListItem.setQuantity(totalQuantity);
    } else {
      WishListItem wishListItem = new WishListItem();
      wishListItem.setWishList(wishList);
      wishListItem.setProduct(product);
      wishListItem.setQuantity(quantity);
      wishList.getItems().add(wishListItem);
    }

    wishListRepository.save(wishList);
  }

  @Override
  public void removeProductFromWishList(Long userId, Long productId, int quantity) {
    WishList wishList = getWishListByUser(userId);

    WishListItem existingWishListItem = wishList.getItems().stream()
        .filter(item -> item.getProduct().getId().equals(productId))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Product not found in wishlist with id: " + productId));

    int remainingQuantity = existingWishListItem.getQuantity() - quantity;
    if (remainingQuantity > 0) {
      existingWishListItem.setQuantity(remainingQuantity);
    } else {
      wishList.getItems().remove(existingWishListItem);
    }

    wishListRepository.save(wishList);
  }

  private WishList createWishListForUser(Long userId) {
    WishList wishList = new WishList();
    wishList.setUser(new User());
    wishList.getUser().setId(userId);
    return wishListRepository.save(wishList);
  }
}
