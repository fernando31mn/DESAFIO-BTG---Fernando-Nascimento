package com.btg.desafio.messaging;

import com.btg.desafio.dto.OrderMessage;
import com.btg.desafio.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    private final OrderService orderService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = "${app.queue.orders}")
    public void receive(OrderMessage msg) throws Exception {
        orderService.processOrderMessage(msg);
    }
}
