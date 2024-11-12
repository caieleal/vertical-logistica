package com.example.vertical.logistica.service;

import com.example.vertical.logistica.core.adapters.controller.dto.UserDTO;
import com.example.vertical.logistica.core.adapters.repository.UserRepository;
import com.example.vertical.logistica.core.domain.entities.UserEntity;
import com.example.vertical.logistica.core.domain.model.Order;
import com.example.vertical.logistica.core.domain.model.Product;
import com.example.vertical.logistica.core.domain.model.User;
import com.example.vertical.logistica.core.mapper.UserMapper;
import com.example.vertical.logistica.core.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class UserServiceImplTest extends BaseServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testSaveAll_NonPersistedUsers(){
        List<UserEntity> userEntities = mockUser().stream().map(UserMapper::toEntity).toList();
        when(userRepository.findAllByUserFileIdIn(anyList())).thenReturn(Collections.emptyList());
        when(userRepository.saveAll(userEntities)).thenReturn(userEntities);

        List<UserDTO> users = userService.saveAll(mockUser());
        assertEquals(1, users.size());
        verify(userRepository, times(1)).saveAll(userEntities);
    }
    @Test
    public void testSaveAll_WithPersistedUsers() {
        List<User> users = mockUser();
        UserEntity userEntity = UserMapper.toEntity(users.get(0));

        when(userRepository.findAllByUserFileIdIn(anyList())).thenReturn(List.of(userEntity));

        List<UserDTO> result = userService.saveAll(users);
        UserDTO resultEntityDto = UserMapper.toDto(userEntity);

        assertEquals(1, result.size());
        assertEquals(resultEntityDto, result.get(0));
        verify(userRepository, times(0)).saveAll(anyList());
    }
    @Test
    public void testFindAllByFileUserId() {
        List<Long> userIds = List.of(1L, 2L);

        when(userRepository.findAllByUserFileIdIn(userIds)).thenReturn(mockUserEntity());

        List<UserEntity> result = userService.findAllByFileUserId(userIds);

        assertEquals(2, result.size());
        assertEquals("Augustus Aufderhar", result.get(0).getName());
        assertEquals("Brenton Orn", result.get(1).getName());
        verify(userRepository, times(1)).findAllByUserFileIdIn(userIds);
    }

    private List<UserEntity> mockUserEntity(){
        UserEntity u1 = new UserEntity();
        u1.setId(UUID.randomUUID());
        u1.setName("Augustus Aufderhar");
        u1.setUserFileId(1L);
        UserEntity u2 = new UserEntity();
        u2.setId(UUID.randomUUID());
        u2.setName("Brenton Orn");
        u2.setUserFileId(2L);

        return List.of(u1, u2);
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
