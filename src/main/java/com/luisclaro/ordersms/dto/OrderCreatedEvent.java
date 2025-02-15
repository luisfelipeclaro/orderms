package com.luisclaro.ordersms.dto;

import java.util.List;

public record OrderCreatedEvent(
        Long orderId,
        Long costumerId,
        List<OrderItemEvent> items
) {}
