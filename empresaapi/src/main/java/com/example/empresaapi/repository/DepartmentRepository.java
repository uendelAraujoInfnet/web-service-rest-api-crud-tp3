package com.example.empresaapi.repository;

import com.example.empresaapi.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {}
