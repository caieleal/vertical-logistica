package com.example.vertical.logistica.core.adapters.repository;

import com.example.vertical.logistica.core.domain.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByOrderFileId(Long id);
    List<OrderEntity> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
}
