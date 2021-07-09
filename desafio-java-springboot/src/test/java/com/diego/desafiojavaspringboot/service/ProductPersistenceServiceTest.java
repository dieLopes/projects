package com.diego.desafiojavaspringboot.service;

import com.diego.desafiojavaspringboot.builder.ProductBuilder;
import com.diego.desafiojavaspringboot.exception.ProductBadRequestException;
import com.diego.desafiojavaspringboot.exception.ProductNotFoundException;
import com.diego.desafiojavaspringboot.model.Product;
import com.diego.desafiojavaspringboot.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductPersistenceServiceTest {

    @InjectMocks
    private ProductPersistenceService productPersistenceService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductSearchService productSearchService;

    @Test
    public void whenCreateProductThenSaveProduct () {
        Product product = build();
        product.setId(null);
        when(productRepository.save(eq(product))).thenReturn(product);
        productPersistenceService.save(product);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void whenCreateProductWithIdThenReturnException () {
        Product product = build();
        assertThatThrownBy(() -> productPersistenceService.save(product))
            .isInstanceOf(ProductBadRequestException.class)
            .hasMessage("O identificador do produto deve estar nulo");
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void whenUpdateProductThenSaveProduct () {
        Product product = build();
        when(productSearchService.findById(eq(product.getId()))).thenReturn(product);
        when(productRepository.save(eq(product))).thenReturn(product);
        productPersistenceService.update(product.getId(), product);
        verify(productRepository).save(any(Product.class));
        verify(productSearchService).findById(eq(product.getId()));
    }

    @Test
    public void whenUpdateProductWithoutIdThenReturnException () {
        Product product = build();
        product.setId(null);
        assertThatThrownBy(() ->  productPersistenceService.update(product.getId(), product))
            .isInstanceOf(ProductBadRequestException.class)
            .hasMessage("O identificador do produto não pode estar nulo");
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void whenUpdateProductNotFoundThenReturnException () {
        Product product = build();
        when(productSearchService.findById(eq(product.getId())))
            .thenThrow(new ProductNotFoundException("Produto não encontrado"));
        assertThatThrownBy(() -> productPersistenceService.update(product.getId(), product))
            .isInstanceOf(ProductNotFoundException.class)
            .hasMessage("Produto não encontrado");
        verify(productRepository, never()).save(any(Product.class));
        verify(productSearchService, times(1)).findById(eq(product.getId()));
    }

    @Test
    public void whenDeleteProductThenRemoveFromDatabase () throws ProductNotFoundException {
        Product product = build();
        when(productSearchService.findById(eq(product.getId()))).thenReturn(product);
        productPersistenceService.delete(product.getId());
        verify(productSearchService).findById(eq(product.getId()));
        verify(productRepository).delete(eq(product));
    }

    @Test
    public void whenDeleteProductButNotFoundThenReturnException () throws ProductNotFoundException {
        Product product = build();
        when(productSearchService.findById(eq(product.getId())))
            .thenThrow(new ProductNotFoundException("Produto não encontrado"));
        assertThatThrownBy(() ->  productPersistenceService.delete(product.getId()))
            .isInstanceOf(ProductNotFoundException.class)
            .hasMessage("Produto não encontrado");
        verify(productSearchService).findById(eq(product.getId()));
        verify(productRepository, never()).delete(eq(product));
    }

    private Product build () {
        return ProductBuilder.of()
                .id("some-id")
                .name("Some Name")
                .description("Some Description")
                .price(BigDecimal.TEN)
                .build();
    }
}
