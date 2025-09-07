package com.example.empresaapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String sku;

    @PositiveOrZero
    private Integer stock;

    @PositiveOrZero
    private Double price;
}
