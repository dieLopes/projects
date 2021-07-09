package com.diego.desafiojavaspringboot.controller;

import com.diego.desafiojavaspringboot.mapper.ProductMapper;
import com.diego.desafiojavaspringboot.model.Product;
import com.diego.desafiojavaspringboot.model.dto.ProductDTO;
import com.diego.desafiojavaspringboot.model.dto.ProductResponseDTO;
import com.diego.desafiojavaspringboot.service.ProductPersistenceService;
import com.diego.desafiojavaspringboot.service.ProductSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductPersistenceService productPersistenceService;
    private ProductSearchService productSearchService;

    public ProductController(ProductPersistenceService productPersistenceService,
                             ProductSearchService productSearchService) {
        this.productPersistenceService = productPersistenceService;
        this.productSearchService = productSearchService;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(ProductMapper.parseEntityToDTO(productSearchService.findById(id)));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponseDTO>> get() {
        return ResponseEntity.ok(ProductMapper.parseEntitiesToDTOs(productSearchService.findAll()));
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponseDTO>> search(
            @RequestParam(name = "q", required = false) String search,
            @RequestParam(name = "max_price", required = false) BigDecimal maxPrice,
            @RequestParam(name = "min_price", required = false) BigDecimal minPrice) {
        return ResponseEntity.ok(ProductMapper.parseEntitiesToDTOs(
                productSearchService.findByFilter(search, maxPrice, minPrice)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO save (@NonNull @RequestBody ProductDTO productDTO) throws Exception{
        Product product = ProductMapper.parseDtoToEntity(productDTO);
        return ProductMapper.parseEntityToDTO(productPersistenceService.save(product));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO update (@NonNull @PathVariable String id,
                                      @NonNull @RequestBody ProductDTO productDTO) {
        Product product = ProductMapper.parseDtoToEntity(productDTO);
        return ProductMapper.parseEntityToDTO(productPersistenceService.update(id, product));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity delete (@PathVariable String id) {
        productPersistenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
