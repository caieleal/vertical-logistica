package com.example.vertical.logistica.core.usecase;

import com.example.vertical.logistica.core.adapters.controller.dto.OrderDTO;
import com.example.vertical.logistica.core.domain.model.Order;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    List<OrderDTO> findByOrderId(Long orderId);
    List<OrderDTO> saveAll(List<Order> orders);
    List<OrderDTO> findByDates(LocalDate startDate, LocalDate endDate, Pageable pageable);
    List<OrderDTO> listAll();
}