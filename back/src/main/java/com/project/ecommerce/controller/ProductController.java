package com.project.ecommerce.controller;

import com.project.ecommerce.dto.ProductDTO;
import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.payload.response.GenericResponse;
import com.project.ecommerce.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @PostMapping
  public ResponseEntity<GenericResponse> createProduct(@RequestBody ProductDTO productDTO) {
    try {
      Product createdProduct = productService.save(productDTO);
      return ResponseEntity.ok(new GenericResponse("Product created successfully ! ID :" + createdProduct.getId()));

    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new GenericResponse("Error: " + e.getMessage()));
    }
  }

  @GetMapping
  public ResponseEntity<List<ProductDTO>> getAllProducts() {
    return ResponseEntity.ok(productService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
    ProductDTO product = productService.findById(id);
    if (product == null) {
      throw new ResourceNotFoundException("Product not found with id: " + id);
    }
    return ResponseEntity.ok(product);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse> deleteProduct(@PathVariable Long id) {
    try {
      productService.delete(id);
      return ResponseEntity.ok(new GenericResponse("Product deleted successfully ! ID :" + id));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new GenericResponse("Error: " + e.getMessage()));
    }
  }

  // Add a new endpoint to update a product
  @PatchMapping("/{id}")
  public ResponseEntity<GenericResponse> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
    try {
      Product updatedProduct = productService.update(id, productDTO);
      return ResponseEntity.ok(new GenericResponse("Product updated successfully ! ID :" + updatedProduct.getId()));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new GenericResponse("Error: " + e.getMessage()));
    }
  }


}
