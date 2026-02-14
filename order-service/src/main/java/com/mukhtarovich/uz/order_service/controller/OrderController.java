package com.mukhtarovich.uz.order_service.controller;

import com.mukhtarovich.uz.order_service.dto.ApiResponse;
import com.mukhtarovich.uz.order_service.dto.OrderRequest;
import com.mukhtarovich.uz.order_service.model.Order;
import com.mukhtarovich.uz.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Order>> createOrder(@RequestBody OrderRequest orderRequest){
        ApiResponse<Order> orderApiResponse = orderService.placeOrder(orderRequest);
        return ResponseEntity.status(orderApiResponse.getCode()).body(orderApiResponse);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        List<Order> allOrders = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,HttpStatus.OK, "Orders found", allOrders));
    }
}
