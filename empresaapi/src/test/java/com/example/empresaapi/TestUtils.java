package com.example.empresaapi;

import com.example.empresaapi.model.*;

public class TestUtils {
    public static Employee employee(Long id) {
        return Employee.builder()
                .id(id)
                .name("Ana")
                .email("ana@example.com")
                .role("Analyst")
                .salary(3000.0)
                .build();
    }

    public static Product product(Long id) {
        return Product.builder()
                .id(id)
                .name("Parafuso")
                .sku("PAR-001")
                .stock(100)
                .price(0.5)
                .build();
    }

    public static Supplier supplier(Long id) {
        return Supplier.builder()
                .id(id)
                .name("Fornecedor ABC")
                .contactName("Maria")
                .email("contato@abc.com")
                .phone("+551199999999")
                .build();
    }

    public static Customer customer(Long id) {
        return Customer.builder()
                .id(id)
                .name("Cliente X")
                .email("cliente@x.com")
                .phone("+552199999999")
                .address("Rua A, 123")
                .build();
    }

    public static Department department(Long id) {
        return Department.builder()
                .id(id)
                .name("Produção")
                .description("Equipe de produção")
                .build();
    }
}
