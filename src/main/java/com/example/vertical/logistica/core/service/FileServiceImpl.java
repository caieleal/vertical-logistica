package com.example.vertical.logistica.core.service;

import com.example.vertical.logistica.core.domain.model.FileInputData;
import com.example.vertical.logistica.core.adapters.controller.dto.OrderDTO;
import com.example.vertical.logistica.core.factory.OrderFactory;
import com.example.vertical.logistica.core.usecase.FileService;
import com.example.vertical.logistica.core.usecase.OrderService;
import com.example.vertical.logistica.core.utils.FileBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    private final FileBuilder fileBuilder;
    private final OrderService orderService;

    public FileServiceImpl(FileBuilder fileBuilder, OrderService orderService) {
        this.fileBuilder = fileBuilder;
        this.orderService = orderService;
    }
    @Override
    public List<OrderDTO> save(List<MultipartFile> files){
        List<FileInputData> fileInputData = fileBuilder.toFileInputData(files);
        return orderService.saveAll(OrderFactory.ordersFactory(fileInputData));
    }
}
