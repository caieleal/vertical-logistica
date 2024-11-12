package com.example.vertical.logistica.controller;

import com.example.vertical.logistica.core.adapters.controller.FileController;
import com.example.vertical.logistica.core.adapters.controller.dto.OrderDTO;
import com.example.vertical.logistica.core.adapters.controller.dto.ProductDTO;
import com.example.vertical.logistica.core.adapters.controller.dto.UserDTO;
import com.example.vertical.logistica.core.usecase.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
public class FileControllerTest extends BaseControllerTest{

    @MockBean
    private FileService fileService;

    @Test
    public void testUpload() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("files",
                "data_1.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Content test".getBytes()
                );

        when(fileService.save(List.of(mockMultipartFile))).thenReturn(mockOrderDto());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/vertical-logistica/upload")
                        .file(mockMultipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Morgan Erdman"))
                .andExpect(jsonPath("$[0].userId").value("82"))
                .andExpect(jsonPath("$[0].orders").isArray())
                .andExpect(jsonPath("$[0].orders.length()").value(1))
                .andExpect(jsonPath("$[0].orders[0].orderId").value(5001L))
                .andExpect(jsonPath("$[0].orders[0].total").value(50.00))
                .andExpect(jsonPath("$[0].orders[0].date").exists())
                .andExpect(jsonPath("$[0].orders[0].products").isArray())
                .andExpect(jsonPath("$[0].orders[0].products.length()").value(1))
                .andExpect(jsonPath("$[0].orders[0].products[0].productId").value(1001L))
                .andExpect(jsonPath("$[0].orders[0].products[0].value").value(50.00));

    }

    private List<OrderDTO> mockOrderDto(){
        UserDTO user = new UserDTO();
        user.setName("Morgan Erdman");
        user.setUserId(82L);
        ProductDTO product = new ProductDTO();
        product.setProductId(1001L);
        product.setValue(new BigDecimal("50.00"));

        OrderDTO order = new OrderDTO();
        order.setOrderId(5001L);
        order.setDate(LocalDate.now());
        order.setUser(user);
        order.setProduct(Collections.singletonList(product));
        return List.of(order);
    }

}
