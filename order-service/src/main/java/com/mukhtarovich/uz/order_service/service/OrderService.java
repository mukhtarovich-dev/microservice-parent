package com.mukhtarovich.uz.order_service.service;

import com.mukhtarovich.uz.order_service.dto.ApiResponse;
import com.mukhtarovich.uz.order_service.dto.InventoryResponse;
import com.mukhtarovich.uz.order_service.dto.OrderLIneItemsDto;
import com.mukhtarovich.uz.order_service.dto.OrderRequest;
import com.mukhtarovich.uz.order_service.model.Order;
import com.mukhtarovich.uz.order_service.model.OrderLIneItems;
import com.mukhtarovich.uz.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public ApiResponse<Order> placeOrder(OrderRequest orderRequest)  {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLIneItems> orderLIneItems = orderRequest.getOrderLIneItemsDto()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLIneItems(orderLIneItems);

        List<String> skuCodes = order.getOrderLIneItems()
                .stream()
                .map(OrderLIneItems::getSkuCode)
                .toList();

        InventoryResponse[] inventoryResponses = webClientBuilder.build()
                .get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        if (inventoryResponses == null || inventoryResponses.length != skuCodes.size()) {
            return new ApiResponse<>(false,HttpStatus.NOT_FOUND,"Same product is not found" ,order);
        }
        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
            return new ApiResponse<>(true, HttpStatus.CREATED,"Order created",order);
        } else {
            return new ApiResponse<>(false,HttpStatus.NOT_FOUND,"Product is not in stock",order);
        }
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
