package com.example.empresaapi.service;

import com.example.empresaapi.TestUtils;
import com.example.empresaapi.exception.ResourceNotFoundException;
import com.example.empresaapi.model.Department;
import com.example.empresaapi.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock DepartmentRepository repo;
    @InjectMocks DepartmentService service;

    @Test
    void create_shouldSave() {
        Department d = TestUtils.department(null);
        Department saved = TestUtils.department(1L);
        when(repo.save(d)).thenReturn(saved);
        Department res = service.create(d);
        assertEquals(1L, res.getId());
    }

    @Test
    void get_missing_shouldThrow() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void update_existing_shouldSave() {
        Department exist = TestUtils.department(1L);
        Department upd = TestUtils.department(null);
        upd.setDescription("desc nova");
        when(repo.findById(1L)).thenReturn(Optional.of(exist));
        when(repo.save(any(Department.class))).thenReturn(upd);
        Department res = service.update(1L, upd);
        assertEquals("desc nova", res.getDescription());
    }

    @Test
    void delete_missing_shouldThrow() {
        when(repo.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
    }

    @Test
    void list_shouldReturnAll() {
        when(repo.findAll()).thenReturn(List.of(TestUtils.department(1L)));
        assertEquals(1, service.findAll().size());
    }
}
