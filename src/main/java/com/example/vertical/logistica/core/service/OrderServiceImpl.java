package com.example.vertical.logistica.core.service;

import com.example.vertical.logistica.core.domain.model.User;
import com.example.vertical.logistica.core.usecase.OrderService;
import com.example.vertical.logistica.core.domain.model.Order;
import com.example.vertical.logistica.core.adapters.controller.dto.OrderDTO;

import com.example.vertical.logistica.core.domain.entities.OrderEntity;
import com.example.vertical.logistica.core.mapper.OrderMapper;
import com.example.vertical.logistica.core.adapters.repository.OrderRepository;
import com.example.vertical.logistica.core.usecase.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(UserService userService, OrderRepository orderRepository) {
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    @Override
    public  List<OrderDTO> findByOrderId(Long orderId) {
        return orderRepository.findByOrderFileId(orderId).stream().map(OrderMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public  List<OrderDTO> saveAll(List<Order> orders) {
        List<User> users = orders.stream().map(Order::getUser).toList();

        List<OrderEntity> orderEntitiesToSave = OrderMapper.toEntity(orders, userService.saveMapUsersByName(users));

        return orderRepository.saveAllAndFlush(orderEntitiesToSave)
                .stream()
                .map(OrderMapper::toDTO)
                .toList();
    }

    @Override
    public List<OrderDTO> findByDates(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findAllByDateBetween(startDate, endDate).stream().map(OrderMapper::toDTO).toList();
    }

    @Override
    public List<OrderDTO> listAll() {
        return orderRepository.findAll().stream().map(OrderMapper::toDTO).toList();
    }

}