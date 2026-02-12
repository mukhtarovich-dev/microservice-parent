package com.mukhtarovich.uz.order_service.repository;

import com.mukhtarovich.uz.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface OrderRepository extends JpaRepository<Order, Long> {
}
