package com.example.empresaapi.service;

import com.example.empresaapi.exception.ResourceNotFoundException;
import com.example.empresaapi.model.Department;
import com.example.empresaapi.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department create(Department department) {
        return departmentRepository.save(department);
    }

    public Department update(Long id, Department updatedDepartment) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found: " +id));
        department.setName(updatedDepartment.getName());
        department.setDescription(updatedDepartment.getDescription());
        return departmentRepository.save(department);
    }

    public Department findById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found: " +id));
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public void delete(Long id) {
        if(!departmentRepository.existsById(id)) throw new ResourceNotFoundException("Department not found: " +id);
        departmentRepository.deleteById(id);
    }
}
