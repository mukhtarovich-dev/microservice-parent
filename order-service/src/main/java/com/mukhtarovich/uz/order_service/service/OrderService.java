package com.mukhtarovich.uz.order_service.service;

import com.mukhtarovich.uz.order_service.dto.OrderLIneItemsDto;
import com.mukhtarovich.uz.order_service.dto.OrderRequest;
import com.mukhtarovich.uz.order_service.model.Order;
import com.mukhtarovich.uz.order_service.model.OrderLIneItems;
import com.mukhtarovich.uz.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    public Order placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLIneItems> orderLIneItems = orderRequest.getOrderLIneItemsDto().stream().map(this::mapToDto).toList();
        order.setOrderLIneItems(orderLIneItems);
        orderRepository.save(order);
        return order;
    }

    private OrderLIneItems mapToDto(OrderLIneItemsDto orderLIneItemsDto) {
        OrderLIneItems orderLIneItems = new OrderLIneItems();
        orderLIneItems.setPrice(orderLIneItemsDto.getPrice());
        orderLIneItems.setQuantity(orderLIneItemsDto.getQuantity());
        orderLIneItems.setSkuCode(orderLIneItemsDto.getSkuCode());
        return orderLIneItems;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
