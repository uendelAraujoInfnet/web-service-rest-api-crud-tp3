package com.example.empresaapi.service;

import com.example.empresaapi.exception.ResourceNotFoundException;
import com.example.empresaapi.model.Customer;
import com.example.empresaapi.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer update(Long id, Customer updatedCustomer) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found: " +id));
        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setPhone(updatedCustomer.getPhone());
        customer.setAddress(updatedCustomer.getAddress());
        return customerRepository.save(customer);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found: " +id));
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public void delete(Long id) {
        if(!customerRepository.existsById(id)) throw new ResourceNotFoundException("Customer not found: " +id);
        customerRepository.deleteById(id);
    }
}
