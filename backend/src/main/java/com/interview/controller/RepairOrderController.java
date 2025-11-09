package com.interview.controller;

import com.interview.dto.CreateOrderDto;
import com.interview.dto.DetailOrderDto;
import com.interview.dto.ListOrderDto;
import com.interview.service.RepairOrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class RepairOrderController {

  private final RepairOrderService repairOrderService;

  public RepairOrderController(RepairOrderService repairOrderService) {
    this.repairOrderService = repairOrderService;
  }

  @GetMapping
  public ResponseEntity<Page<ListOrderDto>> getAllOrders(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(repairOrderService.getAllOrders(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<DetailOrderDto> getOrderById(@PathVariable Long id) {
    return repairOrderService
        .getOrderById(id)
        .map(order -> ResponseEntity.ok(order))
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<DetailOrderDto> createOrder(@RequestBody CreateOrderDto createDto) {
    DetailOrderDto created = repairOrderService.createOrder(createDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DetailOrderDto> updateOrder(
      @PathVariable Long id, @RequestBody CreateOrderDto createDto) {
    try {
      DetailOrderDto updated = repairOrderService.updateOrder(id, createDto);
      return ResponseEntity.ok(updated);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    repairOrderService.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }
}
