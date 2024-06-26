package com.example.orders.service;

import com.example.orders.model.Order;
import com.example.orders.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllOrders() {
        Order order = new Order(2L, 1L, LocalDateTime.now(), "NEW", 100.0);
        List<Order> orders = Collections.singletonList(order);

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();
        assertEquals(1, result.size());
        assertEquals(order.getId(), result.get(0).getId());
    }

    @Test
    public void testDeleteOrder() {
        Long orderId = 1L;
        Order order = new Order(orderId, 1L, LocalDateTime.now(), "NEW", 100.0);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        ResponseEntity<Object> response = orderService.deleteOrderById(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order deleted successfully", response.getBody());
    }
}