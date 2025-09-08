package com.example.empresaapi.controller;

import com.example.empresaapi.TestUtils;
import com.example.empresaapi.model.Supplier;
import com.example.empresaapi.service.SupplierService;
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

@WebMvcTest(SupplierController.class)
class SupplierControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @MockBean SupplierService service;

    @Test
    void create_thenReturnCreated() throws Exception {
        Supplier input = TestUtils.supplier(null);
        Supplier saved = TestUtils.supplier(1L);
        when(service.create(any(Supplier.class))).thenReturn(saved);

        mvc.perform(post("/api/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Fornecedor ABC"));

        verify(service, times(1)).create(any(Supplier.class));
    }

    @Test
    void list_thenReturnOk() throws Exception {
        when(service.findAll()).thenReturn(List.of(TestUtils.supplier(1L)));

        mvc.perform(get("/api/suppliers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Fornecedor ABC"));

        verify(service, times(1)).findAll();
    }

    @Test
    void get_thenReturnOk() throws Exception {
        when(service.findById(1L)).thenReturn(TestUtils.supplier(1L));

        mvc.perform(get("/api/suppliers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update_thenReturnOk() throws Exception {
        Supplier s = TestUtils.supplier(1L);
        s.setContactName("Maria Silva");
        when(service.update(eq(1L), any(Supplier.class))).thenReturn(s);

        mvc.perform(put("/api/suppliers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(s)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactName").value("Maria Silva"));

        verify(service, times(1)).update(eq(1L), any(Supplier.class));
    }

    @Test
    void delete_thenReturnNoContent() throws Exception {
        doNothing().when(service).delete(1L);

        mvc.perform(delete("/api/suppliers/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }
}
