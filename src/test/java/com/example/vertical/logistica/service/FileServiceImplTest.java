package com.example.vertical.logistica.service;

import com.example.vertical.logistica.core.adapters.controller.dto.OrderDTO;
import com.example.vertical.logistica.core.adapters.controller.dto.ProductDTO;
import com.example.vertical.logistica.core.adapters.controller.dto.UserDTO;
import com.example.vertical.logistica.core.domain.model.FileInputData;
import com.example.vertical.logistica.core.service.FileServiceImpl;
import com.example.vertical.logistica.core.usecase.OrderService;
import com.example.vertical.logistica.core.utils.FileBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class FileServiceImplTest extends BaseServiceTest{
    @Mock
    private FileBuilder fileBuilder;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private FileServiceImpl fileService;

    @Test
    public void testSave(){
        MockMultipartFile mockFile = new MockMultipartFile("file", "data.txt", "text/plain", "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308".getBytes());

        List<MultipartFile> files = List.of(mockFile);

        List<FileInputData> fileInputDataList = mockFileInputData();
        List<OrderDTO> ordersMock = mockOrder();
        when(fileBuilder.toFileInputData(files)).thenReturn(fileInputDataList);
        when(orderService.saveAll(anyList())).thenReturn(ordersMock);

        List<OrderDTO> result = fileService.save(files);

        assertEquals(ordersMock, result);
        OrderDTO resultOrder = result.get(0);
        ProductDTO product = resultOrder.getProduct().get(0);

        assertEquals(82L, resultOrder.getUser().getUserId());
        assertEquals("Morgan Erdman", resultOrder.getUser().getName());

        assertEquals(80L, resultOrder.getOrderId());
        assertEquals(LocalDate.of(2021, 3, 8), resultOrder.getDate());

        assertEquals(1L, product.getProductId());
        assertEquals(new BigDecimal("50.00"), product.getValue());

        verify(fileBuilder, times(1)).toFileInputData(files);
        verify(orderService, times(1)).saveAll(anyList());

    }
    private List<OrderDTO> mockOrder(){
        UserDTO user = new UserDTO();
        user.setName("Morgan Erdman");
        user.setUserId(82L);
        ProductDTO product = new ProductDTO();
        product.setProductId(1L);
        product.setValue(new BigDecimal("50.00"));

        OrderDTO order = new OrderDTO();
        order.setOrderId(80L);
        order.setDate(LocalDate.of(2021, 3, 8));
        order.setUser(user);
        order.setProduct(Collections.singletonList(product));
        return List.of(order);
    }
    private List<FileInputData> mockFileInputData(){
        FileInputData.User user = FileInputData.User.builder()
                .userId(70L)
                .userName("Palmer Prosacco")
                .build();

        FileInputData.Product product = FileInputData.Product.builder()
                .productId(753L)
                .value(new BigDecimal("1836.74"))
                .build();

        FileInputData fileInputData = FileInputData.builder()
                .orderId(3L)
                .user(user)
                .product(product)
                .date(LocalDate.of(2021, 3, 8))
                .build();
        return List.of(fileInputData);
    }
}
