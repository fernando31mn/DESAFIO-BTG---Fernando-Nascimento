import com.example.btg.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Test
    void shouldReturnOrderTotal() {
        OrderService service = mock(OrderService.class);
        when(service.getOrderTotal(1L)).thenReturn(new BigDecimal("10.00"));
        var controller = new com.example.btg.controller.OrderController(service);

        ResponseEntity<?> response = controller.getOrderTotal(1L);
        Map<?, ?> body = (Map<?, ?>) response.getBody();

        assertThat(body).containsEntry("codigoPedido", 1L).containsEntry("total", new BigDecimal("10.00"));
    }
}