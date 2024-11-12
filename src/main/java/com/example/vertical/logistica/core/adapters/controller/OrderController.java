package com.example.vertical.logistica.core.adapters.controller;

import com.example.vertical.logistica.core.adapters.controller.response.VerticalLogResponse;
import com.example.vertical.logistica.core.usecase.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/vertical-logistica/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Listar todos os registros de order, user e product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar todos os registros de order, user e product",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VerticalLogResponse.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<VerticalLogResponse>> list() {
        return ResponseEntity.ok(VerticalLogResponse.fromDto(orderService.listAll()));
    }
    @Operation(summary = "Filtra por orderId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtra orderId",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VerticalLogResponse.class))),
            @ApiResponse(responseCode = "404", description = "Resultados não encontrados por este orderId")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/filter/{id}")
    public ResponseEntity<List<VerticalLogResponse>> filterByOrderId(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(VerticalLogResponse
                .fromDto(orderService.findByOrderId(id)));
    }
    @Operation(summary = "Filtra as ordens utilizando filtro de data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtra as ordens utilizando filtro de data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VerticalLogResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros de data inválidos")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/filter/{startDate}/{endDate}")
    public ResponseEntity<List<VerticalLogResponse>> filterByDate(
            @PathVariable(value = "startDate") LocalDate startDate,
            @PathVariable(value = "endDate")LocalDate endDate) {
        return ResponseEntity.ok(VerticalLogResponse.fromDto(orderService.findByDates(startDate, endDate)));
    }
}
