package com.example.orders.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;

    @NotNull(message = "La fecha de creación es obligatoria")
    private LocalDateTime creationDate;

    @NotBlank(message = "El estado de la orden es obligatorio")
    private String status;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(message = "El precio debe ser mayor a 0", value = "0.0")
    private Double total;
}
