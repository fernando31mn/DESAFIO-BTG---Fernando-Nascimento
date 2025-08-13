package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class OrderEntity {
    @Id
    private Long id; // codigoPedido

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    private OffsetDateTime createdAt = OffsetDateTime.now();

    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public OrderEntity(Long id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }
}
