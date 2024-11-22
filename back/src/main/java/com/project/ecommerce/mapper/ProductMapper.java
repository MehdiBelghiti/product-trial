package com.project.ecommerce.mapper;

import com.project.ecommerce.dto.ProductDTO;
import com.project.ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 * I added product mapper only, to highlight the usage of mappers in the project
 */
@Mapper
public interface ProductMapper {
  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);



  Product ProductDTOtoProduct(ProductDTO dto);


  ProductDTO ProductToProductDTO(Product entity);
}
