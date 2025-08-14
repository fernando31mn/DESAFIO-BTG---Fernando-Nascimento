package com.btg.desafio.controller;

import com.btg.desafio.model.Customer;
import com.btg.desafio.model.OrderEntity;
import com.btg.desafio.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrderTotal() {
        Long orderId = 1L;
        BigDecimal expectedTotal = new BigDecimal("100.50");

        when(orderService.getOrderTotal(orderId)).thenReturn(expectedTotal);

        ResponseEntity<?> response = orderController.getOrderTotal(orderId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedTotal, ((java.util.Map<?, ?>)response.getBody()).get("total"));
        assertEquals(orderId, ((java.util.Map<?, ?>)response.getBody()).get("codigoPedido"));

        verify(orderService, times(1)).getOrderTotal(orderId);
    }

    @Test
    void testCountOrders() {
        Long customerId = 2L;
        long expectedCount = 5L;

        when(orderService.countOrdersByCustomer(customerId)).thenReturn(expectedCount);

        ResponseEntity<?> response = orderController.countOrders(customerId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedCount, ((java.util.Map<?, ?>)response.getBody()).get("quantidadePedidos"));
        assertEquals(customerId, ((java.util.Map<?, ?>)response.getBody()).get("codigoCliente"));

        verify(orderService, times(1)).countOrdersByCustomer(customerId);
    }

    @Test
    void testListOrders() {
        Long customerId = 3L;
        Customer customer = new Customer();
        customer.setId(customerId);

        List<OrderEntity> orders = List.of(
                new OrderEntity(1L, customer),
                new OrderEntity(2L, customer)
        );
        orders.get(0).setTotal(new BigDecimal("50.00"));
        orders.get(1).setTotal(new BigDecimal("75.00"));

        when(orderService.listOrdersByCustomer(customerId)).thenReturn(orders);

        ResponseEntity<List<OrderEntity>> response = orderController.listOrders(customerId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(orders, response.getBody());

        verify(orderService, times(1)).listOrdersByCustomer(customerId);
    }
}