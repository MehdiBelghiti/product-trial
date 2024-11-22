package com.project.ecommerce.service;

import com.project.ecommerce.dto.ProductDTO;
import com.project.ecommerce.model.Product;
import java.util.List;

public interface ProductService {

  Product save(ProductDTO product);

  List<ProductDTO> findAll();

  ProductDTO findById(Long id);

  void delete(Long id);

  Product update(Long id, ProductDTO product);
}
