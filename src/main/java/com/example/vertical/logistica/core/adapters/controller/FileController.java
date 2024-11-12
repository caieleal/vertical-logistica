package com.example.vertical.logistica.core.adapters.controller;

import com.example.vertical.logistica.core.adapters.controller.response.VerticalLogResponse;
import com.example.vertical.logistica.core.usecase.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/vertical-logistica")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/upload")
    public ResponseEntity<List<VerticalLogResponse>> upload(@RequestParam("files") List<MultipartFile> files) {
        return ResponseEntity.ok(VerticalLogResponse.fromDto(fileService.save(files)));
    }
}
