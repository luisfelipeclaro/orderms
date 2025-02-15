package com.luisclaro.ordersms.dto;

import com.luisclaro.ordersms.entity.OrderEntity;

import java.math.BigDecimal;

public record OrderResponse(
        Long orderId,
        Long costumerId,
        BigDecimal total
) {

    public static OrderResponse toDto(OrderEntity entity) {
        return new OrderResponse(entity.getOrderId(), entity.getCustomerId(), entity.getTotal());
    }

}
