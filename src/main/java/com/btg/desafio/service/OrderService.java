package com.btg.desafio.service;


import com.btg.desafio.dto.OrderMessage;
import com.btg.desafio.model.Customer;
import com.btg.desafio.model.OrderEntity;
import com.btg.desafio.model.OrderItem;
import com.btg.desafio.repository.CustomerRepository;
import com.btg.desafio.repository.OrderItemRepository;
import com.btg.desafio.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(CustomerRepository customerRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public void processOrderMessage(OrderMessage msg) {
        Customer customer = customerRepository.findById(msg.getCodigoCliente())
                .orElseGet(() -> new Customer(msg.getCodigoCliente(), "Cliente-" + msg.getCodigoCliente()));
        customerRepository.save(customer);

        OrderEntity order = new OrderEntity(msg.getCodigoPedido(), customer);
        orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;
        for (OrderMessage.Item it : msg.getItens()) {
            BigDecimal line = it.getPreco().multiply(BigDecimal.valueOf(it.getQuantidade()));
            OrderItem item = new OrderItem(order, it.getProduto(), it.getQuantidade(), it.getPreco(), line);
            order.getItems().add(item);
            total = total.add(line);
        }
        order.setTotal(total);
        orderRepository.save(order);
    }

    public BigDecimal getOrderTotal(Long orderId) {
        return orderRepository.findById(orderId).map(o -> o.getTotal()).orElse(BigDecimal.ZERO);
    }

    public long countOrdersByCustomer(Long customerId) {
        return orderRepository.countByCustomerId(customerId);
    }

    public List<OrderEntity> listOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}
