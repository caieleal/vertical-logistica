package com.example.vertical.logistica.service;

import com.example.vertical.logistica.core.adapters.controller.dto.OrderDTO;
import com.example.vertical.logistica.core.adapters.controller.dto.ProductDTO;
import com.example.vertical.logistica.core.adapters.repository.OrderRepository;
import com.example.vertical.logistica.core.domain.entities.OrderEntity;
import com.example.vertical.logistica.core.domain.entities.ProductEntity;
import com.example.vertical.logistica.core.domain.entities.UserEntity;
import com.example.vertical.logistica.core.domain.model.Order;
import com.example.vertical.logistica.core.domain.model.Product;
import com.example.vertical.logistica.core.domain.model.User;
import com.example.vertical.logistica.core.service.OrderServiceImpl;
import com.example.vertical.logistica.core.usecase.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest extends BaseServiceTest{

    @Mock
    private UserService userService;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testFindByOrderId() {
        Long orderId = 10L;

        when(orderRepository.findByOrderFileId(orderId)).thenReturn(mockOrderEntity());
        List<OrderDTO> result = orderService.findByOrderId(orderId);
        OrderDTO order = result.get(0);
        assertEquals(1, result.size());
        assertEquals(orderId, order.getOrderId());

        assertEquals("Augustus Aufderhar", order.getUser().getName());
        assertEquals(1, order.getProduct().size());

        ProductDTO product = order.getProduct().get(0);
        assertEquals(2L, product.getProductId());
        assertEquals(new BigDecimal("152.30"), product.getValue());

        verify(orderRepository, times(1)).findByOrderFileId(orderId);
    }
    @Test
    public void testSaveAll() {
        Map<String, UserEntity> userEntityMap = Map.of("Augustus Aufderhar", new UserEntity(UUID.randomUUID(), 2L, "Augustus Aufderhar"));

        when(userService.saveMapUsersByName(mockUser())).thenReturn(userEntityMap);
        when(orderRepository.saveAllAndFlush(anyList())).thenReturn(mockOrderEntity());

        List<OrderDTO> result = orderService.saveAll(mockOrder());
        OrderDTO order = result.get(0);

        assertEquals(1, result.size());
        assertEquals(10L, order.getOrderId());

        assertEquals("Augustus Aufderhar", order.getUser().getName());
        assertEquals(1, order.getProduct().size());

        ProductDTO product = order.getProduct().get(0);
        assertEquals(2L, product.getProductId());
        assertEquals(new BigDecimal("152.30"), product.getValue());
        verify(userService, times(1)).saveMapUsersByName(mockUser());
        verify(orderRepository, times(1)).saveAllAndFlush(anyList());
    }
    @Test
    public void testFindByDates() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        when(orderRepository.findAllByDateBetween(eq(startDate), eq(endDate))).thenReturn(mockOrderEntity());
        List<OrderDTO> result = orderService.findByDates(startDate, endDate);

        assertEquals(1, result.size());
        OrderDTO order = result.get(0);

        assertEquals(1, result.size());
        assertEquals(10L, order.getOrderId());

        assertEquals("Augustus Aufderhar", order.getUser().getName());
        assertEquals(1, order.getProduct().size());

        ProductDTO product = order.getProduct().get(0);
        assertEquals(2L, product.getProductId());
        assertEquals(new BigDecimal("152.30"), product.getValue());

        verify(orderRepository, times(1)).findAllByDateBetween(startDate, endDate);
    }
    @Test
    public void testListAll() {
        when(orderRepository.findAll()).thenReturn(mockOrderEntity());
        List<OrderDTO> result = orderService.listAll();

        assertEquals(1, result.size());

        verify(orderRepository, times(1)).findAll();
    }

    private List<OrderEntity> mockOrderEntity(){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(UUID.randomUUID());
        orderEntity.setOrderFileId(10L);
        orderEntity.setDate(LocalDate.of(2023, 1, 1));

        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setName("Augustus Aufderhar");
        userEntity.setUserFileId(2L);
        orderEntity.setUser(userEntity);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(UUID.randomUUID());
        productEntity.setValue(new BigDecimal("152.30"));
        productEntity.setProductFileId(2L);
        productEntity.setOrderEntity(orderEntity);

        orderEntity.setProducts(List.of(productEntity));
        orderEntity.setUser(userEntity);

        return List.of(orderEntity);
    }
    private List<User> mockUser(){
        User user = new User();
        user.setUserId(2L);
        user.setName("Augustus Aufderhar");
        return List.of(user);
    }
    private List<Order> mockOrder(){
        Order order = new Order();
        order.setOrderId(2L);
        order.setDate(LocalDate.of(2023, 1, 1));
        order.setProducts(mockProduct());
        order.setUser(mockUser().get(0));
        return List.of(order);
    }
    private List<Product> mockProduct(){
        Product product = new Product();
        product.setProductFileId(10L);
        product.setValue(new BigDecimal("235.00"));
        return List.of(product);
    }

}
