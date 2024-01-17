package com.security.spring_security_course.controller;

import com.security.spring_security_course.entity.Product;
import com.security.spring_security_course.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;


    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAll(){
        List<Product> products = productRepository.findAll();
        if (!products.isEmpty()){
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/products")
    @PreAuthorize("hasPermission('HttpStatus','SAVE_ONE_PRODUCT')")
    ResponseEntity<Product> createProduct(@RequestBody @Valid Product product){
        return new ResponseEntity<>(productRepository.save(product),HttpStatus.CREATED);
    }
}
