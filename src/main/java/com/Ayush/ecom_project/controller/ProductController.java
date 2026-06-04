package com.Ayush.ecom_project.controller;

import com.Ayush.ecom_project.model.Product;
import com.Ayush.ecom_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("api/products")
    public List<Product> getAllProducts(){
        return service.getAllProduct();
    }
}
