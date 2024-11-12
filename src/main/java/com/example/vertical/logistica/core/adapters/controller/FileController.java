package com.example.vertical.logistica.core.adapters.controller;

import com.example.vertical.logistica.core.adapters.controller.response.VerticalLogResponse;
import com.example.vertical.logistica.core.usecase.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Upload de arquivos",
            description = "Recebe uma lista de arquivos e retorna uma lista com os dados processados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Upload realizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VerticalLogResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<List<VerticalLogResponse>> upload(@RequestParam("files") List<MultipartFile> files) {
        return ResponseEntity.ok(VerticalLogResponse.fromDto(fileService.save(files)));
    }
}
