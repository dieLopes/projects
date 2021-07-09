package com.diego.desafiojavaspringboot.mapper;

import com.diego.desafiojavaspringboot.model.Product;
import com.diego.desafiojavaspringboot.model.dto.ProductDTO;
import com.diego.desafiojavaspringboot.model.dto.ProductResponseDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static Product parseDtoToEntity(ProductDTO productDTO) {
        return mapper.map(productDTO, Product.class);
    }

    public static ProductResponseDTO parseEntityToDTO(Product product) {
        return mapper.map(product, ProductResponseDTO.class);
    }

    public static List<ProductResponseDTO> parseEntitiesToDTOs(List<Product> products) {
        return products
            .stream()
            .map(ProductMapper::parseEntityToDTO)
            .collect(Collectors.toList());
    }
}
