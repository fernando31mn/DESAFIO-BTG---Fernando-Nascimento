package com.btg.desafio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private OrderEntity order;

    private String product;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal lineTotal;

    public OrderItem(OrderEntity order, String product, Integer quantity, BigDecimal price, BigDecimal lineTotal) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.lineTotal = lineTotal;
    }
}
