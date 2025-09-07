package com.example.empresaapi.service;

import com.example.empresaapi.exception.ResourceNotFoundException;
import com.example.empresaapi.model.Supplier;
import com.example.empresaapi.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier create(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier update(Long id, Supplier updatedSupplier) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " +id));
        supplier.setName(updatedSupplier.getName());
        supplier.setContactName(updatedSupplier.getContactName());
        supplier.setEmail(updatedSupplier.getEmail());
        supplier.setPhone(updatedSupplier.getPhone());
        return supplierRepository.save(supplier);
    }

    public Supplier findById(Long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " +id));
    }

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public void delete(Long id) {
        if(!supplierRepository.existsById(id)) throw new ResourceNotFoundException("Supplier not found: " +id);
        supplierRepository.deleteById(id);
    }
}
