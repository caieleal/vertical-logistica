package com.example.vertical.logistica.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FileInputData {

    private Long orderId;
    private User user;
    private Product product;
    private LocalDate date;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class User {
        private Long userId;
        private String userName;

    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Product {
        private Long productId;
        private BigDecimal value;
    }
}
