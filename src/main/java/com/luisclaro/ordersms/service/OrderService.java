package com.luisclaro.ordersms.service;

import com.luisclaro.ordersms.dto.OrderCreatedEvent;
import com.luisclaro.ordersms.dto.OrderResponse;
import com.luisclaro.ordersms.entity.OrderEntity;
import com.luisclaro.ordersms.entity.OrderItem;
import com.luisclaro.ordersms.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    public final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void save(OrderCreatedEvent event) {
        OrderEntity order = new OrderEntity();
        order.setOrderId(event.orderId());
        order.setCustomerId(event.costumerId());
        order.setTotal(getTotal(event));
        order.setItems(getOrderItems(event));
        orderRepository.save(order);
    }

    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest) {
        Page<OrderEntity> allByCustomerId = orderRepository.findAllByCustomerId(customerId, pageRequest);
        return allByCustomerId.map(OrderResponse::toDto);
    }

    private static List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.items().stream()
                .map(item ->
                        new OrderItem(item.productName(), item.quantity(), item.price()))
                .toList();
    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event
                .items()
                .stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

}
