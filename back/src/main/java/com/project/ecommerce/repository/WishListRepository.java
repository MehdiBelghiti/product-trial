package com.project.ecommerce.repository;

import com.project.ecommerce.model.WishList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
  Optional<WishList> findByUserId(Long userId);
}
