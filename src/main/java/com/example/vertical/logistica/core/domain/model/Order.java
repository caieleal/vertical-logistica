package com.example.vertical.logistica.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private Long orderId;
    private LocalDate date;
    private User user;
    private List<Product> products;

    public static class OrderBuilder {
        private Long orderId;
        private LocalDate date;
        private List<Product> products = new ArrayList<>();
        private User user;

        public OrderBuilder date(List<FileInputData> fileInputData){
            if(Objects.nonNull(fileInputData) && !fileInputData.isEmpty()){
                FileInputData dto = fileInputData.stream().findFirst().get();
                this.date = dto.getDate();
            }
            return this;
        }
        public OrderBuilder product(List<FileInputData> fileInputData){
            fileInputData.forEach(f -> add(Product.builder()
                    .productFileId(f.getProduct().getProductId())
                    .value(f.getProduct().getValue())
                    .build()));
            return this;
        }
        public OrderBuilder user(List<FileInputData> users){
            if(Objects.nonNull(users) && !users.isEmpty()){
                FileInputData fileInputData = users.stream().findFirst().get();
                this.user = new User(fileInputData.getUser().getUserId(), fileInputData.getUser().getUserName());
            }
            return this;
        }
        private void add(Product product){
            this.products.add(product);
        }
    }
}
