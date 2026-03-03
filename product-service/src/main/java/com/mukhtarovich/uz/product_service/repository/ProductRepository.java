package com.mukhtarovich.uz.product_service.repository;

import com.mukhtarovich.uz.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface ProductRepository  extends JpaRepository<Product, Long> {
}
