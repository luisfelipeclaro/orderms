package com.luisclaro.ordersms.dto;

import java.math.BigDecimal;

public record OrderItemEvent(
        String productName,
        Integer quantity,
        BigDecimal price
) {}
