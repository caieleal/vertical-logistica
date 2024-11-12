package com.example.vertical.logistica.core.factory;

import com.example.vertical.logistica.core.domain.model.FileInputData;
import com.example.vertical.logistica.core.domain.model.Order;
import com.example.vertical.logistica.core.utils.FileUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OrderFactory {

    public static List<Order> ordersFactory(List<FileInputData> filesInputData) {
        Map<String, List<FileInputData>> filesGrouped = FileUtils.groupByUserName(filesInputData);
        return filesGrouped.values()
                .stream()
                .map(files -> Order.builder()
                        .orderId(files.get(0).getOrderId())
                        .date(files)
                        .user(files)
                        .product(files)
                        .build())
                .toList();
    }
}
