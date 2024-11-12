package com.example.vertical.logistica.core.adapters.controller.response;

import com.example.vertical.logistica.core.adapters.controller.dto.OrderDTO;
import com.example.vertical.logistica.core.adapters.controller.dto.ProductDTO;
import com.example.vertical.logistica.core.adapters.controller.dto.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VerticalLogResponse {
    @Schema(example = "50")
    private Long userId;
    @Schema(example = "Dr. Jonathon Cummerata")
    private String name;
    private List<Order> orders;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Order{
        @Schema(example = "1")
        private Long orderId;

        @Schema(example = "150")
        private BigDecimal total;
        @Schema(example = "2021-11-22")
        private LocalDate date;
        private List<Product> products;

        public static Order fromDto(OrderDTO order){
            return Order.builder().orderId(order.getOrderId())
                    .date(order.getDate())
                    .total(order.getProduct().stream()
                            .map(ProductDTO::getValue)
                            .reduce(BigDecimal.ZERO, BigDecimal::add))
                    .products(order.getProduct().stream()
                            .map(Product::fromDto)
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Product {
        @Schema(example = "1")
        private Long productId;
        @Schema(example = "150")
        private BigDecimal value;

        public static Product fromDto(ProductDTO product) {
            return Product.builder().productId(product.getProductId()).value(product.getValue()).build();
        }
    }
    public static List<VerticalLogResponse> fromDto(List<OrderDTO> dto){
        Map<String, List<OrderDTO> > ordersByUser = dto.stream()
                .collect(Collectors.groupingBy(order -> order.getUser().getName()));

        return ordersByUser.values().stream()
                .map(order -> {

                    UserDTO user = order.get(0).getUser();
                    List<Order> responseOrders = order.stream()
                            .map(Order::fromDto)
                            .collect(Collectors.toList());

                    return VerticalLogResponse.builder()
                            .userId(user.getUserId())
                            .name(user.getName())
                            .orders(responseOrders)
                            .build();
                })
                .collect(Collectors.toList());
        }
}
