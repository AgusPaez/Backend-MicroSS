package com.example.orders.controller;

import jakarta.validation.Valid;
import com.example.orders.model.Order;
import com.example.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Método para obtener todas las órdenes
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Método para obtener una orden por su ID
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));
    }

    // Método para crear una nueva orden
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        } else {
            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        }
    }

    // Método para eliminar una orden por su ID
    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderById(@PathVariable Long orderId) {
        orderService.deleteOrderById(orderId);
    }

    // Método para actualizar una orden existente
    @PutMapping("/{orderId}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long orderId, @Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        } else {
            Order updatedOrder = orderService.updateOrder(orderId, order);
            return ResponseEntity.ok(updatedOrder);
        }
    }
}