package com.example.empresaapi.controller;

import com.example.empresaapi.TestUtils;
import com.example.empresaapi.model.Customer;
import com.example.empresaapi.service.CustomerService;
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

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @MockBean CustomerService service;

    @Test
    void create_thenReturnCreated() throws Exception {
        Customer input = TestUtils.customer(null);
        Customer saved = TestUtils.customer(1L);
        when(service.create(any(Customer.class))).thenReturn(saved);

        mvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Cliente X"));

        verify(service, times(1)).create(any(Customer.class));
    }

    @Test
    void list_thenReturnOk() throws Exception {
        when(service.findAll()).thenReturn(List.of(TestUtils.customer(1L)));

        mvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cliente X"));

        verify(service, times(1)).findAll();
    }

    @Test
    void get_thenReturnOk() throws Exception {
        when(service.findById(1L)).thenReturn(TestUtils.customer(1L));

        mvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update_thenReturnOk() throws Exception {
        Customer c = TestUtils.customer(1L);
        c.setAddress("Rua A, 123 - apt 2");
        when(service.update(eq(1L), any(Customer.class))).thenReturn(c);

        mvc.perform(put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(c)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("Rua A, 123 - apt 2"));

        verify(service, times(1)).update(eq(1L), any(Customer.class));
    }

    @Test
    void delete_thenReturnNoContent() throws Exception {
        doNothing().when(service).delete(1L);

        mvc.perform(delete("/api/customers/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }
}
