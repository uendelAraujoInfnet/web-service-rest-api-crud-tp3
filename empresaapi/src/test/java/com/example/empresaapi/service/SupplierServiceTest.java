package com.example.empresaapi.service;

import com.example.empresaapi.TestUtils;
import com.example.empresaapi.exception.ResourceNotFoundException;
import com.example.empresaapi.model.Supplier;
import com.example.empresaapi.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {

    @Mock SupplierRepository repo;
    @InjectMocks SupplierService service;

    @Test
    void create_shouldSave() {
        Supplier s = TestUtils.supplier(null);
        Supplier saved = TestUtils.supplier(1L);
        when(repo.save(s)).thenReturn(saved);
        Supplier res = service.create(s);
        assertEquals(1L, res.getId());
    }

    @Test
    void get_missing_shouldThrow() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void update_existing_shouldSave() {
        Supplier exist = TestUtils.supplier(1L);
        Supplier upd = TestUtils.supplier(null);
        upd.setContactName("Contato");
        when(repo.findById(1L)).thenReturn(Optional.of(exist));
        when(repo.save(any(Supplier.class))).thenReturn(upd);
        Supplier res = service.update(1L, upd);
        assertEquals("Contato", res.getContactName());
    }

    @Test
    void delete_missing_shouldThrow() {
        when(repo.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
    }

    @Test
    void list_shouldReturnAll() {
        when(repo.findAll()).thenReturn(List.of(TestUtils.supplier(1L)));
        assertEquals(1, service.findAll().size());
    }
}
