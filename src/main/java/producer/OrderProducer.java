package producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.OrderMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.queue.orders}")
    private String ordersQueue;

    public OrderProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendTestMessage(OrderMessage message) throws Exception {
        String json = objectMapper.writeValueAsString(message);
        rabbitTemplate.convertAndSend(ordersQueue, json);
    }
}
