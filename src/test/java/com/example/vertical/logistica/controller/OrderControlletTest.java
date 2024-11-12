package com.example.vertical.logistica.controller;

import com.example.vertical.logistica.core.adapters.controller.OrderController;
import com.example.vertical.logistica.core.adapters.controller.dto.OrderDTO;
import com.example.vertical.logistica.core.adapters.controller.dto.ProductDTO;
import com.example.vertical.logistica.core.adapters.controller.dto.UserDTO;
import com.example.vertical.logistica.core.usecase.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControlletTest extends BaseControllerTest {
    private static final String URL = "/vertical-logistica/order";

    @MockBean
    private OrderService orderService;

    @Test
    public void testList() throws Exception {
        when(orderService.listAll()).thenReturn(mockOrder());
        mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value(82L))
                .andExpect(jsonPath("$[0].name").value("Morgan Erdman"))
                .andExpect(jsonPath("$[0].orders[0].orderId").value(100L))
                .andExpect(jsonPath("$[0].orders[0].total").value(680.0))
                .andExpect(jsonPath("$[0].orders[0].products[0].productId").value(1001L))
                .andExpect(jsonPath("$[0].orders[0].products[0].value").value(530.00))
                .andExpect(jsonPath("$[0].orders[0].products[1].productId").value(1002L))
                .andExpect(jsonPath("$[0].orders[0].products[1].value").value(150.00));
    }
    @Test
    public void testFilterByOrderId() throws Exception {
        Long orderId = 100L;
        when(orderService.findByOrderId(orderId)).thenReturn(mockOrder());
        mockMvc.perform(get(URL.concat("/filter/{id}"), orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value(82L))
                .andExpect(jsonPath("$[0].name").value("Morgan Erdman"))
                .andExpect(jsonPath("$[0].orders[0].orderId").value(100L))
                .andExpect(jsonPath("$[0].orders[0].total").value(680.0))
                .andExpect(jsonPath("$[0].orders[0].products[0].productId").value(1001L))
                .andExpect(jsonPath("$[0].orders[0].products[0].value").value(530.00))
                .andExpect(jsonPath("$[0].orders[0].products[1].productId").value(1002L))
                .andExpect(jsonPath("$[0].orders[0].products[1].value").value(150.00));
    }

    @Test
    public void testFilterByDates() throws Exception {
        LocalDate startDate = LocalDate.of(2021, 3, 4);
        LocalDate endDate = LocalDate.of(2024, 3, 7);

        when(orderService.findByDates(startDate, endDate)).thenReturn(mockOrder());
        mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/filter/{startDate}/{endDate}"), startDate, endDate)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value(82L))
                .andExpect(jsonPath("$[0].name").value("Morgan Erdman"))
                .andExpect(jsonPath("$[0].orders[0].orderId").value(100L))
                .andExpect(jsonPath("$[0].orders[0].total").value(680.0))
                .andExpect(jsonPath("$[0].orders[0].products[0].productId").value(1001L))
                .andExpect(jsonPath("$[0].orders[0].products[0].value").value(530.00))
                .andExpect(jsonPath("$[0].orders[0].products[1].productId").value(1002L))
                .andExpect(jsonPath("$[0].orders[0].products[1].value").value(150.00));
    }

    private List<OrderDTO> mockOrder(){
        UserDTO user = new UserDTO();
        user.setName("Morgan Erdman");
        user.setUserId(82L);
        ProductDTO p = new ProductDTO();
        p.setProductId(1001L);
        p.setValue(new BigDecimal("530.00"));

        ProductDTO p1 = new ProductDTO();
        p1.setProductId(1002L);
        p1.setValue(new BigDecimal("150.00"));

        OrderDTO order = new OrderDTO();
        order.setOrderId(100L);
        order.setDate(LocalDate.now());
        order.setUser(user);
        order.setProduct(List.of(p, p1));
        return List.of(order);
    }

}
