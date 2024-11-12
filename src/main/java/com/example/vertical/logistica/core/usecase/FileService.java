package com.example.vertical.logistica.core.usecase;

import com.example.vertical.logistica.core.adapters.controller.dto.OrderDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<OrderDTO> save(List<MultipartFile> files);
}
