package com.example.empresaapi.controller;

import com.example.empresaapi.model.Employee;
import com.example.empresaapi.service.EmployeeService;
import com.example.empresaapi.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @MockBean EmployeeService service;

    @Test
    void create_thenReturnCreated() throws Exception {
        Employee input = TestUtils.employee(null);
        Employee saved = TestUtils.employee(1L);
        when(service.create(any(Employee.class))).thenReturn(saved);

        mvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ana"));

        verify(service, times(1)).create(any(Employee.class));
    }

    @Test
    void create_whenInvalid_thenBadRequest() throws Exception {
        // missing email -> validation should fail
        Employee invalid = TestUtils.employee(null);
        invalid.setEmail(""); // invalid

        mvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void list_thenReturnOk() throws Exception {
        Employee e = TestUtils.employee(1L);
        when(service.findAll()).thenReturn(List.of(e));

        mvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ana"));

        verify(service, times(1)).findAll();
    }

    @Test
    void get_thenReturnOk() throws Exception {
        Employee e = TestUtils.employee(1L);
        when(service.findById(1L)).thenReturn(e);

        mvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(service, times(1)).findById(1L);
    }

    @Test
    void update_thenReturnOk() throws Exception {
        Employee updated = TestUtils.employee(1L);
        updated.setRole("Senior");
        when(service.update(eq(1L), any(Employee.class))).thenReturn(updated);

        mvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("Senior"));

        verify(service, times(1)).update(eq(1L), any(Employee.class));
    }

    @Test
    void delete_thenReturnNoContent() throws Exception {
        doNothing().when(service).delete(1L);

        mvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }
}
