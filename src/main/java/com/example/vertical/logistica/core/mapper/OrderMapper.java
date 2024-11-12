package com.example.vertical.logistica.core.mapper;

import com.example.vertical.logistica.core.adapters.controller.dto.OrderDTO;
import com.example.vertical.logistica.core.domain.entities.OrderEntity;
import com.example.vertical.logistica.core.domain.entities.ProductEntity;
import com.example.vertical.logistica.core.domain.entities.UserEntity;
import com.example.vertical.logistica.core.domain.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OrderMapper {

    public static OrderDTO toDTO(OrderEntity orderEntity){
        OrderDTO order = new OrderDTO();
        order.setOrderId(orderEntity.getOrderFileId());
        order.setDate(orderEntity.getDate());
        order.setUser(UserMapper.toDto(orderEntity.getUser()));
        order.setProduct(ProductMapper.toDto(orderEntity.getProducts()));
        return order;
    }
    public static OrderEntity toEntity(Order order, UserEntity userEntity){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderFileId(order.getOrderId());
        orderEntity.setDate(order.getDate());
        orderEntity.setUser(userEntity);
        orderEntity.getProducts().addAll(order.getProducts()
                .stream()
                .map(product -> {
                    ProductEntity productEntity = ProductMapper.toEntity(product);
                    productEntity.setOrderEntity(orderEntity);
                    return productEntity;
                }).toList());

        return orderEntity;
    }
    public static List<OrderEntity> toEntity(List<Order> orders, Map<String, UserEntity> userMap){
        return orders.stream()
                .map(order -> OrderMapper.toEntity(order, userMap.get(order.getUser().getName())))
                .toList();
    }
}
