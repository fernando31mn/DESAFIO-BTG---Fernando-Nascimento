package com.btg.desafio.repository;

import com.btg.desafio.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    long countByCustomerId(Long customerId);
    List<OrderEntity> findByCustomerId(Long customerId);
}
