package com.example.orders.service;

import com.example.orders.model.Order;
import com.example.orders.repositories.OrderRepository;
import com.example.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final RestTemplate restTemplate = new RestTemplate();
    @Autowired

    private OrderRepository orderRepository;

    // Método para obtener todas las órdenes
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Método para obtener una orden por su ID
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    // Método para crear una nueva orden
    public ResponseEntity<Object> newOrder(Order order) {
        Long productId = order.getProductId();
        String url = "http://localhost:8083/products/find/" + productId;
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Producto encontrado, guardar la orden
                orderRepository.save(order);
                return new ResponseEntity<>("Order created !", HttpStatus.CREATED);
            } else {
                // Producto no encontrado, no guardar la orden
                return new ResponseEntity<>("Product not found, order not created", HttpStatus.NOT_FOUND);
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return new ResponseEntity<>("Product not found, order not created", HttpStatus.NOT_FOUND);
        }
    }


    // Método para eliminar una orden por su ID
    public ResponseEntity<Object> deleteOrderById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            orderRepository.deleteById(orderId);
            return ResponseEntity.status(HttpStatus.OK).body("Order deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
    }

    // Método para actualizar una orden existente
    public Order updateOrder(Long orderId, Order updatedOrder) {
        Optional<Order> existingOrderOptional = orderRepository.findById(orderId);
        if (existingOrderOptional.isPresent()) {
            Order existingOrder = existingOrderOptional.get();
            existingOrder.setStatus(updatedOrder.getStatus());
            existingOrder.setTotal(updatedOrder.getTotal());
            existingOrder.setProductId(updatedOrder.getProductId());
            return orderRepository.save(existingOrder);
        } else {
            throw new IllegalArgumentException("No se encontró la orden con el ID proporcionado");
        }
    }
}
