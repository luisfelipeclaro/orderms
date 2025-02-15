package com.luisclaro.ordersms.listener;

import static com.luisclaro.ordersms.config.RabbitMqConfig.ORDER_CREATED_QUEUE;

import com.luisclaro.ordersms.dto.OrderCreatedEvent;
import com.luisclaro.ordersms.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final Logger LOGGER = LoggerFactory.getLogger(OrderCreatedListener.class);

    private final OrderService orderService;

    public OrderCreatedListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEvent> message) {
        LOGGER.info("Message consumed: {}", message.getPayload());
        orderService.save(message.getPayload());
        LOGGER.info("Order saved.");
    }

}
