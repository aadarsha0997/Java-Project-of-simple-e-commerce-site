package com.Ayush.ecom_project.repo;

import com.Ayush.ecom_project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Integer> {
}
