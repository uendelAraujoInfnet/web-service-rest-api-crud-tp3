package com.example.empresaapi.service;

import com.example.empresaapi.TestUtils;
import com.example.empresaapi.exception.ResourceNotFoundException;
import com.example.empresaapi.model.Product;
import com.example.empresaapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock ProductRepository repo;
    @InjectMocks ProductService service;

    @Test
    void create_shouldSave() {
        Product p = TestUtils.product(null);
        Product saved = TestUtils.product(1L);
        when(repo.save(p)).thenReturn(saved);

        Product result = service.create(p);
        assertEquals(1L, result.getId());
    }

    @Test
    void get_missing_shouldThrow() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void update_existing_shouldSave() {
        Product exist = TestUtils.product(1L);
        Product update = TestUtils.product(null);
        update.setStock(200);
        when(repo.findById(1L)).thenReturn(Optional.of(exist));
        when(repo.save(any(Product.class))).thenReturn(update);
        Product res = service.update(1L, update);
        assertEquals(200, res.getStock());
    }

    @Test
    void delete_missing_shouldThrow() {
        when(repo.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
    }

    @Test
    void list_shouldReturnAll() {
        when(repo.findAll()).thenReturn(List.of(TestUtils.product(1L)));
        assertEquals(1, service.findAll().size());
    }
}
