package com.Ayush.ecom_project.controller;

import com.Ayush.ecom_project.model.Product;
import com.Ayush.ecom_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("api/products")
    public ResponseEntity<List<Product>> getAllProducts(){

        return new ResponseEntity<>(service.getAllProduct(),HttpStatus.OK);
    }

    @GetMapping("api/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
       Product product= service.getProductById(id);
        if(product !=null)
            return new ResponseEntity<>(product,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}

