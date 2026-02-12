package com.mukhtarovich.uz.order_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {
    private Boolean status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse(Boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
