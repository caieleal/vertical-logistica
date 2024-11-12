package com.example.vertical.logistica.core.adapters.controller;

import com.example.vertical.logistica.core.adapters.controller.response.VerticalLogResponse;
import com.example.vertical.logistica.core.usecase.OrderService;
import org.springframework.data.domain.Pageable;
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<VerticalLogResponse>> list() {
        return ResponseEntity.ok(VerticalLogResponse.fromDto(orderService.listAll()));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/filter/{id}")
    public ResponseEntity<List<VerticalLogResponse>> filterByOrderId(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(VerticalLogResponse
                .fromDto(orderService.findByOrderId(id)));
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/filter/{startDate}/{endDate}")
    public ResponseEntity<List<VerticalLogResponse>> filterByDate(
            @PathVariable(value = "startDate") LocalDate startDate,
            @PathVariable(value = "endDate")LocalDate endDate,
            Pageable pageable) {
        return ResponseEntity.ok(VerticalLogResponse.fromDto(orderService.findByDates(startDate, endDate, pageable)));
    }
}
