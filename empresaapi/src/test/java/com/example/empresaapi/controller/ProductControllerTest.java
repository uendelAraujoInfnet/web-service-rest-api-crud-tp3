package com.example.empresaapi.controller;

import com.example.empresaapi.model.Product;
import com.example.empresaapi.service.ProductService;
import com.example.empresaapi.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @MockBean ProductService service;

    @Test
    void create_thenReturnCreated() throws Exception {
        Product input = TestUtils.product(null);
        Product saved = TestUtils.product(1L);
        when(service.create(any(Product.class))).thenReturn(saved);

        mvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.sku").value("PAR-001"));

        verify(service, times(1)).create(any(Product.class));
    }

    @Test
    void list_thenReturnOk() throws Exception {
        when(service.findAll()).thenReturn(List.of(TestUtils.product(1L)));

        mvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Parafuso"));

        verify(service, times(1)).findAll();
    }

    @Test
    void get_thenReturnOk() throws Exception {
        when(service.findById(1L)).thenReturn(TestUtils.product(1L));

        mvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(service, times(1)).findById(1L);
    }

    @Test
    void update_thenReturnOk() throws Exception {
        Product p = TestUtils.product(1L);
        p.setStock(200);
        when(service.update(eq(1L), any(Product.class))).thenReturn(p);

        mvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(p)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock").value(200));

        verify(service, times(1)).update(eq(1L), any(Product.class));
    }

    @Test
    void delete_thenReturnNoContent() throws Exception {
        doNothing().when(service).delete(1L);

        mvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }
}
