package com.Ayush.ecom_project.service;

import com.Ayush.ecom_project.model.Product;
import com.Ayush.ecom_project.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;
    public List<Product> getAllProduct(){
        return repo.findAll();
    }

    public Product getProductById(int id) {

        return  repo.findById(id).orElse(new Product());
    }
}
