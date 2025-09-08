package com.example.empresaapi.repository;

import com.example.empresaapi.model.*;
import com.example.empresaapi.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class RepositoryIntegrationTest {

    @Autowired EmployeeRepository employeeRepository;
    @Autowired ProductRepository productRepository;
    @Autowired SupplierRepository supplierRepository;
    @Autowired CustomerRepository customerRepository;
    @Autowired DepartmentRepository departmentRepository;

    @Test
    void employee_crud() {
        Employee e = TestUtils.employee(null);
        Employee saved = employeeRepository.save(e);
        assertThat(saved.getId()).isNotNull();

        var found = employeeRepository.findById(saved.getId()).orElseThrow();
        assertThat(found.getEmail()).isEqualTo("ana@example.com");

        employeeRepository.deleteById(saved.getId());
        assertThat(employeeRepository.existsById(saved.getId())).isFalse();
    }

    @Test
    void product_crud() {
        Product p = TestUtils.product(null);
        Product saved = productRepository.save(p);
        assertThat(saved.getId()).isNotNull();
        assertThat(productRepository.findById(saved.getId())).isPresent();
        productRepository.delete(saved);
        assertThat(productRepository.existsById(saved.getId())).isFalse();
    }

    @Test
    void supplier_crud() {
        Supplier s = TestUtils.supplier(null);
        Supplier saved = supplierRepository.save(s);
        assertThat(saved.getId()).isNotNull();
        assertThat(supplierRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void customer_crud() {
        Customer c = TestUtils.customer(null);
        Customer saved = customerRepository.save(c);
        assertThat(saved.getId()).isNotNull();
        assertThat(customerRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void department_crud() {
        Department d = TestUtils.department(null);
        Department saved = departmentRepository.save(d);
        assertThat(saved.getId()).isNotNull();
        assertThat(departmentRepository.findById(saved.getId())).isPresent();
    }
}
