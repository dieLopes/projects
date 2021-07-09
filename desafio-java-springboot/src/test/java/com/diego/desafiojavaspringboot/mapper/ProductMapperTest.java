package com.diego.desafiojavaspringboot.mapper;

import com.diego.desafiojavaspringboot.builder.ProductBuilder;
import com.diego.desafiojavaspringboot.model.Product;
import com.diego.desafiojavaspringboot.model.dto.ProductDTO;
import com.diego.desafiojavaspringboot.model.dto.ProductResponseDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(MockitoJUnitRunner.class)
public class ProductMapperTest {

    @Test
    public void whenConvertDTOToEntityThenReturn () {
        ProductDTO productDTO = buildDTO();
        Product product = ProductMapper.parseDtoToEntity(productDTO);
        assertThat(product.getId()).isNullOrEmpty();
        assertThat(product.getName()).isEqualTo(productDTO.getName());
        assertThat(product.getDescription()).isEqualTo(productDTO.getDescription());
        assertThat(product.getPrice()).isEqualTo(productDTO.getPrice());
    }

    @Test
    public void whenConvertEntityToDTOThenReturn () {
        Product product = ProductBuilder.of()
                .id("some-id")
                .name("Some Name")
                .description("Some Description")
                .price(BigDecimal.TEN)
                .build();
        ProductResponseDTO productResponseDTO = ProductMapper.parseEntityToDTO(product);
        assertThat(productResponseDTO.getId()).isEqualTo(product.getId());
        assertThat(productResponseDTO.getName()).isEqualTo(product.getName());
        assertThat(productResponseDTO.getDescription()).isEqualTo(product.getDescription());
        assertThat(productResponseDTO.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    public void whenConvertEntitiesToListDTOThenReturn () {
        List<Product> products = List.of(
                buildProduct("some-id", "Some Name", "Some Description", BigDecimal.TEN),
                buildProduct("another-id", "Another Name", "Another Description", BigDecimal.ONE));
        List<ProductResponseDTO> productResponseDTOs = ProductMapper.parseEntitiesToDTOs(products);
        assertThat(productResponseDTOs).hasSize(2)
                .extracting(ProductResponseDTO::getId,
                        ProductResponseDTO::getName,
                        ProductResponseDTO::getDescription,
                        ProductResponseDTO::getPrice)
                .containsExactlyInAnyOrder(
                        tuple("some-id", "Some Name", "Some Description", BigDecimal.TEN),
                        tuple("another-id", "Another Name", "Another Description", BigDecimal.ONE)
                );
    }

    private ProductDTO buildDTO() {
        return ProductDTO.Builder.of()
                .name("Some Name")
                .description("Some Description")
                .price(BigDecimal.TEN)
                .build();
    }

    private Product buildProduct (String id, String name, String description, BigDecimal price) {
        return ProductBuilder.of()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .build();
    }
}
