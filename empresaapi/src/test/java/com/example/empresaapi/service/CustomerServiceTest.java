package com.example.empresaapi.service;

import com.example.empresaapi.TestUtils;
import com.example.empresaapi.exception.ResourceNotFoundException;
import com.example.empresaapi.model.Customer;
import com.example.empresaapi.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock CustomerRepository repo;
    @InjectMocks CustomerService service;

    @Test
    void create_shouldSave() {
        Customer c = TestUtils.customer(null);
        Customer saved = TestUtils.customer(1L);
        when(repo.save(c)).thenReturn(saved);
        Customer res = service.create(c);
        assertEquals(1L, res.getId());
    }

    @Test
    void get_missing_shouldThrow() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void update_existing_shouldSave() {
        Customer exist = TestUtils.customer(1L);
        Customer upd = TestUtils.customer(null);
        upd.setAddress("novo end");
        when(repo.findById(1L)).thenReturn(Optional.of(exist));
        when(repo.save(any(Customer.class))).thenReturn(upd);
        Customer res = service.update(1L, upd);
        assertEquals("novo end", res.getAddress());
    }

    @Test
    void delete_missing_shouldThrow() {
        when(repo.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
    }

    @Test
    void list_shouldReturnAll() {
        when(repo.findAll()).thenReturn(List.of(TestUtils.customer(1L)));
        assertEquals(1, service.findAll().size());
    }
}
