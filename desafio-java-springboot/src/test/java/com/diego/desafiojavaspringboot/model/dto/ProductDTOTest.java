package com.diego.desafiojavaspringboot.model.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ProductDTOTest {

    @Test
    public void whenBuildProductThenReturnProduct () {
        ProductDTO product = ProductDTO.Builder.of()
                .name("Some Name")
                .description("Some Description")
                .price(BigDecimal.TEN)
                .build();
        assertThat(product.getName()).isEqualTo("Some Name");
        assertThat(product.getDescription()).isEqualTo("Some Description");
        assertThat(product.getPrice()).isEqualTo(BigDecimal.TEN);
    }
}
