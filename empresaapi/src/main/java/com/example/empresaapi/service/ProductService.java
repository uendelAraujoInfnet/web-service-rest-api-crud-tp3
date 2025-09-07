package com.example.empresaapi.service;

import com.example.empresaapi.exception.ResourceNotFoundException;
import com.example.empresaapi.model.Product;
import com.example.empresaapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product updatedProduct) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found: " +id));
        product.setName(updatedProduct.getName());
        product.setSku(updatedProduct.getSku());
        product.setStock(updatedProduct.getStock());
        product.setPrice(updatedProduct.getPrice());
        return productRepository.save(product);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found: " +id));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void delete(Long id) {
        if(!productRepository.existsById(id)) throw new ResourceNotFoundException("Product not found: " +id);
        productRepository.deleteById(id);
    }
}
