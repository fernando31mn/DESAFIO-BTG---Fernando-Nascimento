
import com.example.btg.dto.OrderMessage;
import com.example.btg.model.Customer;
import com.example.btg.repository.CustomerRepository;
import com.example.btg.repository.OrderItemRepository;
import com.example.btg.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        orderRepository = mock(OrderRepository.class);
        orderItemRepository = mock(OrderItemRepository.class);
        orderService = new OrderService(customerRepository, orderRepository, orderItemRepository);
    }

    @Test
    void shouldProcessOrderMessageAndCalculateTotal() {
        OrderMessage msg = new OrderMessage();
        msg.setCodigoPedido(1L);
        msg.setCodigoCliente(1L);
        OrderMessage.Item item = new OrderMessage.Item();
        item.setProduto("lapis");
        item.setQuantidade(2);
        item.setPreco(new BigDecimal("1.50"));
        msg.setItens(List.of(item));

        when(customerRepository.findById(1L)).thenReturn(java.util.Optional.of(new Customer(1L, "Cliente-1")));

        orderService.processOrderMessage(msg);

        verify(orderRepository, times(2)).save(any());
    }
}