package com.mukhtarovich.uz.product_service.service;

import com.mukhtarovich.uz.product_service.dto.ProductRequest;
import com.mukhtarovich.uz.product_service.dto.ProductResponse;
import com.mukhtarovich.uz.product_service.exeption.ProductNotFoundException;
import com.mukhtarovich.uz.product_service.model.Product;
import com.mukhtarovich.uz.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
 @Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(ProductRequest productRequest) {
        Product product =  Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        log.info("Product {} has been created", product.getId());
        return productRepository.save(product);
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }
    public ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with id: " + id
                ));
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);
        log.info("Product {} has been updated", product.getId());
        return mapToProductResponse(product);
    }
}



