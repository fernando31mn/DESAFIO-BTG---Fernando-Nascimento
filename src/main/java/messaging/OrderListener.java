package messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.OrderMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import service.OrderService;

@Component
public class OrderListener {

    private final OrderService orderService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = "${app.queue.orders}")
    public void receive(String payload) throws Exception {
        OrderMessage msg = objectMapper.readValue(payload, OrderMessage.class);
        orderService.processOrderMessage(msg);
    }
}
