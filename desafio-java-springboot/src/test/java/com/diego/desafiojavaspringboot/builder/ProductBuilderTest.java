package com.diego.desafiojavaspringboot.builder;

import com.diego.desafiojavaspringboot.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ProductBuilderTest {

    @Test
    public void whenBuildProductThenReturnProduct () {
        Product product = ProductBuilder.of()
                .id("some-id")
                .name("Some Name")
                .description("Some Description")
                .price(BigDecimal.TEN)
                .build();
        assertThat(product.getId()).isEqualTo("some-id");
        assertThat(product.getName()).isEqualTo("Some Name");
        assertThat(product.getDescription()).isEqualTo("Some Description");
        assertThat(product.getPrice()).isEqualTo(BigDecimal.TEN);
    }
}
