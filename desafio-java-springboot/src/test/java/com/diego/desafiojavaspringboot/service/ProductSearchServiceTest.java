package com.diego.desafiojavaspringboot.service;

import com.diego.desafiojavaspringboot.builder.ProductBuilder;
import com.diego.desafiojavaspringboot.exception.ProductNotFoundException;
import com.diego.desafiojavaspringboot.model.Product;
import com.diego.desafiojavaspringboot.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductSearchServiceTest {

    @InjectMocks
    private ProductSearchService productSearchService;
    @Mock
    private ProductRepository productRepository;

    @Test
    public void whenFindProductByIdThenReturnProduct () throws ProductNotFoundException {
        Product product = ProductBuilder.of()
                .id("some-id")
                .name("Some name")
                .description("Some description")
                .price(BigDecimal.TEN)
                .build();
        when(productRepository.findById(eq(product.getId()))).thenReturn(Optional.of(product));
        assertThat(productSearchService.findById(product.getId())).satisfies(findProduct -> {
            assertThat(findProduct.getId()).isEqualTo(product.getId());
            assertThat(findProduct.getName()).isEqualTo(product.getName());
            assertThat(findProduct.getDescription()).isEqualTo(product.getDescription());
            assertThat(findProduct.getPrice()).isEqualTo(product.getPrice());
        });
        verify(productRepository).findById(eq(product.getId()));
    }

    @Test
    public void whenFindProductByIdButNotFoundThenReturnException () {
        when(productRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> productSearchService.findById("some-id"))
            .isInstanceOf(ProductNotFoundException.class)
            .hasMessage("Produto não encontrado");
        verify(productRepository).findById(anyString());
    }

    @Test
    public void whenFindAllProductsThenReturnProducts () {
        List<Product> products = List.of(
                ProductBuilder.of()
                        .id("some-id")
                        .name("Some name")
                        .description("Some description")
                        .price(BigDecimal.TEN)
                        .build(),
                ProductBuilder.of()
                        .id("another-id")
                        .name("Another name")
                        .description("Another description")
                        .price(BigDecimal.ONE)
                        .build());
        when(productRepository.findAll()).thenReturn(products);
        assertThat(productSearchService.findAll()).hasSize(2)
            .extracting(Product::getId,
                Product::getName,
                Product::getDescription,
                Product::getPrice)
            .containsExactlyInAnyOrder(
                tuple("some-id", "Some name", "Some description", BigDecimal.TEN),
                tuple("another-id", "Another name", "Another description", BigDecimal.ONE)
            );
        verify(productRepository).findAll();
    }
}