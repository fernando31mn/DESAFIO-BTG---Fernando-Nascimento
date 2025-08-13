package producer;

import dto.OrderMessage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TestProducerRunner implements CommandLineRunner {

    private final OrderProducer producer;

    public TestProducerRunner(OrderProducer producer) {
        this.producer = producer;
    }

    @Override
    public void run(String... args) throws Exception {
        OrderMessage order = new OrderMessage();
        order.setCodigoPedido(2001L);
        order.setCodigoCliente(2L);

        OrderMessage.Item item1 = new OrderMessage.Item();
        item1.setProduto("caneta");
        item1.setQuantidade(50);
        item1.setPreco(new BigDecimal("2.50"));

        OrderMessage.Item item2 = new OrderMessage.Item();
        item2.setProduto("borracha");
        item2.setQuantidade(20);
        item2.setPreco(new BigDecimal("1.00"));

        order.setItens(List.of(item1, item2));

        producer.sendTestMessage(order);
    }
}
