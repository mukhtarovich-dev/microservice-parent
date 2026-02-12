package com.mukhtarovich.uz.product_service.controller;

import com.mukhtarovich.uz.product_service.dto.ApiResponse;
import com.mukhtarovich.uz.product_service.dto.ProductRequest;
import com.mukhtarovich.uz.product_service.dto.ProductResponse;
import com.mukhtarovich.uz.product_service.model.Product;
import com.mukhtarovich.uz.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody ProductRequest productRequest) {

        Product product = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Product created successfully",
                        product
                ));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable("id") String id, @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.updateProduct(id, productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Product updated successfully", productResponse));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }


}
