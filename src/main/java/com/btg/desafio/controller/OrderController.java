package com.btg.desafio.controller;

import com.btg.desafio.model.OrderEntity;
import com.btg.desafio.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/{id}/total")
    public ResponseEntity<?> getOrderTotal(@PathVariable Long id) {
        BigDecimal total = orderService.getOrderTotal(id);
        return ResponseEntity.ok().body(java.util.Map.of("codigoPedido", id, "total", total));
    }

    @GetMapping("/customers/{id}/orders/count")
    public ResponseEntity<?> countOrders(@PathVariable Long id) {
        long c = orderService.countOrdersByCustomer(id);
        return ResponseEntity.ok().body(java.util.Map.of("codigoCliente", id, "quantidadePedidos", c));
    }

    @GetMapping("/customers/{id}/orders")
    public ResponseEntity<List<OrderEntity>> listOrders(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.listOrdersByCustomer(id));
    }
}
