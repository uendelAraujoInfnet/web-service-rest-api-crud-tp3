package com.example.empresaapi.controller;

import com.example.empresaapi.TestUtils;
import com.example.empresaapi.model.Department;
import com.example.empresaapi.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @MockBean DepartmentService service;

    @Test
    void create_thenReturnCreated() throws Exception {
        Department input = TestUtils.department(null);
        Department saved = TestUtils.department(1L);
        when(service.create(any(Department.class))).thenReturn(saved);

        mvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Produção"));

        verify(service, times(1)).create(any(Department.class));
    }

    @Test
    void list_thenReturnOk() throws Exception {
        when(service.findAll()).thenReturn(List.of(TestUtils.department(1L)));

        mvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Produção"));

        verify(service, times(1)).findAll();
    }

    @Test
    void get_thenReturnOk() throws Exception {
        when(service.findById(1L)).thenReturn(TestUtils.department(1L));

        mvc.perform(get("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update_thenReturnOk() throws Exception {
        Department d = TestUtils.department(1L);
        d.setDescription("Nova descrição");
        when(service.update(eq(1L), any(Department.class))).thenReturn(d);

        mvc.perform(put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(d)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Nova descrição"));

        verify(service, times(1)).update(eq(1L), any(Department.class));
    }

    @Test
    void delete_thenReturnNoContent() throws Exception {
        doNothing().when(service).delete(1L);

        mvc.perform(delete("/api/departments/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }
}
