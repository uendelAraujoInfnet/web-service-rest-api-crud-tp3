package com.example.empresaapi.service;

import com.example.empresaapi.exception.ResourceNotFoundException;
import com.example.empresaapi.model.Employee;
import com.example.empresaapi.repository.EmployeeRepository;
import com.example.empresaapi.TestUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock EmployeeRepository repo;
    @InjectMocks EmployeeService service;

    @Test
    void create_shouldSave() {
        Employee e = TestUtils.employee(null);
        Employee saved = TestUtils.employee(1L);
        when(repo.save(e)).thenReturn(saved);

        Employee result = service.create(e);
        assertEquals(1L, result.getId());
        verify(repo, times(1)).save(e);
    }

    @Test
    void get_existing_shouldReturn() {
        when(repo.findById(1L)).thenReturn(Optional.of(TestUtils.employee(1L)));
        Employee result = service.findById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void get_missing_shouldThrow() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void update_existing_shouldSave() {
        Employee exist = TestUtils.employee(1L);
        Employee update = TestUtils.employee(null);
        update.setName("Novo Nome");
        when(repo.findById(1L)).thenReturn(Optional.of(exist));
        when(repo.save(any(Employee.class))).thenAnswer(i -> {
            Employee arg = i.getArgument(0);
            arg.setId(1L);
            return arg;
        });

        Employee result = service.update(1L, update);
        assertEquals("Novo Nome", result.getName());
        verify(repo, times(1)).save(any(Employee.class));
    }

    @Test
    void update_missing_shouldThrow() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.update(1L, TestUtils.employee(null)));
    }

    @Test
    void list_shouldReturnAll() {
        when(repo.findAll()).thenReturn(List.of(TestUtils.employee(1L)));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void delete_missing_shouldThrow() {
        when(repo.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
    }
}
