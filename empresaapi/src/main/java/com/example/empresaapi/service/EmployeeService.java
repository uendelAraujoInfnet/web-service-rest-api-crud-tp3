package com.example.empresaapi.service;

import com.example.empresaapi.exception.ResourceNotFoundException;
import com.example.empresaapi.model.Employee;
import com.example.empresaapi.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(Long id, Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found: " +id));
        employee.setName(updatedEmployee.getName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setRole(updatedEmployee.getRole());
        employee.setSalary(updatedEmployee.getSalary());
        return employeeRepository.save(employee);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found: " +id));
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public void delete(Long id) {
        if(!employeeRepository.existsById(id)) throw new ResourceNotFoundException("Employee not found: " +id);
        employeeRepository.deleteById(id);
    }
}
