package com.example.vertical.logistica.core.mapper;

import com.example.vertical.logistica.core.adapters.controller.dto.ProductDTO;
import com.example.vertical.logistica.core.domain.entities.ProductEntity;
import com.example.vertical.logistica.core.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public static List<ProductDTO> toDto(List<ProductEntity> product){
        return product.stream().map(p -> ProductDTO.builder()
                .productId(p.getProductFileId())
                .value(p.getValue())
                .build()).toList();
    }

    public static ProductEntity toEntity(Product product){
        return ProductEntity.builder()
                .productFileId(product.getProductFileId())
                .value(product.getValue())
                .build();
    }

}
