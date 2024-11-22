package com.project.ecommerce.service.impl;

import com.project.ecommerce.dto.ProductDTO;
import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.mapper.ProductMapper;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.ProductRepository;
import com.project.ecommerce.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Override
  public Product save(ProductDTO productDTO) {
    Product product = ProductMapper.INSTANCE.ProductDTOtoProduct(productDTO);

    return productRepository.save(product);
  }

  @Override
  public List<ProductDTO> findAll() {
    return productRepository.findAll().stream()
        .map(ProductMapper.INSTANCE::ProductToProductDTO)
        .collect(Collectors.toList());
  }

  @Override
  public ProductDTO findById(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    return ProductMapper.INSTANCE.ProductToProductDTO(product);
  }

  @Override
  public void delete(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

    productRepository.delete(product);
  }

  @Override
  public Product update(Long id, ProductDTO productDTO) {

    Product existingProduct = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    Product product = ProductMapper.INSTANCE.ProductDTOtoProduct(productDTO);
    if(existingProduct == null) {
      throw new ResourceNotFoundException("Product not found with id: " + id);
    }

    existingProduct.setName(product.getName() != null ? product.getName() : existingProduct.getName());
    existingProduct.setDescription(product.getDescription() != null ? product.getDescription() : existingProduct.getDescription());
    existingProduct.setCategory(product.getCategory() != null ? product.getCategory() : existingProduct.getCategory());
    existingProduct.setCode(product.getCode() != null ? product.getCode() : existingProduct.getCode());
    existingProduct.setPrice(product.getPrice() != null ? product.getPrice() : existingProduct.getPrice());
    existingProduct.setImage(product.getImage() != null ? product.getImage() : existingProduct.getImage());
    existingProduct.setInternalReference(product.getInternalReference() != null ? product.getInternalReference() : existingProduct.getInternalReference());
    existingProduct.setInventoryStatus(product.getInventoryStatus() != null ? product.getInventoryStatus() : existingProduct.getInventoryStatus());
    existingProduct.setShellId(product.getShellId() != null ? product.getShellId() : existingProduct.getShellId());
    existingProduct.setRating(product.getRating() != null ? product.getRating() : existingProduct.getRating());
    existingProduct.setQuantity(product.getQuantity() != null ? product.getQuantity() : existingProduct.getQuantity());

    return productRepository.save(existingProduct);
  }
}
