package com.btg.desafio.service;

import com.btg.desafio.dto.OrderMessage;
import com.btg.desafio.model.Customer;
import com.btg.desafio.model.OrderEntity;
import com.btg.desafio.repository.CustomerRepository;
import com.btg.desafio.repository.OrderItemRepository;
import com.btg.desafio.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessOrderMessage_NewCustomer() {
        // Simula uma mensagem de pedido
        OrderMessage msg = new OrderMessage();
        msg.setCodigoCliente(1L);
        msg.setCodigoPedido(100L);
        OrderMessage.Item item = new OrderMessage.Item();
        item.setProduto("Produto A");
        item.setPreco(new BigDecimal("10.00"));
        item.setQuantidade(2);
        msg.setItens(List.of(item));

        // Cliente não existe
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        orderService.processOrderMessage(msg);

        // Verifica se o cliente foi criado e salvo
        verify(customerRepository, times(1)).save(any(Customer.class));

        // Verifica se o pedido foi salvo
        verify(orderRepository, times(2)).save(any(OrderEntity.class)); // primeira e segunda vez (antes e depois de setTotal)
    }

    @Test
    void testProcessOrderMessage_ExistingCustomer() {
        Customer existingCustomer = new Customer(1L, "Cliente-1");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));

        OrderMessage msg = new OrderMessage();
        msg.setCodigoCliente(1L);
        msg.setCodigoPedido(101L);
        OrderMessage.Item item = new OrderMessage.Item();
        item.setProduto("Produto B");
        item.setPreco(new BigDecimal("15.00"));
        item.setQuantidade(3);
        msg.setItens(List.of(item));

        orderService.processOrderMessage(msg);

        // Cliente existente não deve ser recriado
        verify(customerRepository, times(1)).save(existingCustomer);

        verify(orderRepository, times(2)).save(any(OrderEntity.class));
    }

    @Test
    void testGetOrderTotal() {
        OrderEntity order = new OrderEntity();
        order.setId(200L);
        order.setTotal(new BigDecimal("123.45"));

        when(orderRepository.findById(200L)).thenReturn(Optional.of(order));

        BigDecimal total = orderService.getOrderTotal(200L);

        assertEquals(new BigDecimal("123.45"), total);
    }

    @Test
    void testGetOrderTotal_OrderNotFound() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        BigDecimal total = orderService.getOrderTotal(999L);

        assertEquals(BigDecimal.ZERO, total);
    }

    @Test
    void testCountOrdersByCustomer() {
        when(orderRepository.countByCustomerId(1L)).thenReturn(5L);

        long count = orderService.countOrdersByCustomer(1L);

        assertEquals(5L, count);
    }

    @Test
    void testListOrdersByCustomer() {
        Customer customer = new Customer(1L, "Cliente-1");
        OrderEntity order1 = new OrderEntity(1L, customer);
        OrderEntity order2 = new OrderEntity(2L, customer);

        when(orderRepository.findByCustomerId(1L)).thenReturn(List.of(order1, order2));

        List<OrderEntity> orders = orderService.listOrdersByCustomer(1L);

        assertEquals(2, orders.size());
        assertEquals(order1, orders.get(0));
        assertEquals(order2, orders.get(1));
    }
}
