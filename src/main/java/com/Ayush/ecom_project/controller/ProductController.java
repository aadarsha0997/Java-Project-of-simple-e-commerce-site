package com.Ayush.ecom_project.controller;

import com.Ayush.ecom_project.model.Product;
import com.Ayush.ecom_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.servlet.autoconfigure.MultipartAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping("/api/product")
    public ResponseEntity<?> addProduct(
            @RequestPart("product") Product product,
            @RequestPart("imageFile")MultipartFile imageFile
            ){
        try {
            service.addProduct(product,imageFile);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Product added sucessfully");
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add product");
        }
    }

    @PutMapping("/api/product/{productId}")
    public ResponseEntity<?>  updateProduct(@PathVariable int productId,
                                                @RequestPart("product") Product product,
                                                @RequestPart("imageFile")MultipartFile imageFile) throws IOException {

        Product updatedproduct=service.updateProduct(productId,product,imageFile);
        if(updatedproduct !=null ){
            return new ResponseEntity<>("Updated",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

    }

    @DeleteMapping("/api/product/{productId}")
    public ResponseEntity<?>deleteProduct(@PathVariable int productId) throws IOException {
        service.deleteProduct(productId);
       return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Product added sucessfully");
    }

    @GetMapping("/api/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        System.out.println(keyword);
        List<Product> products=service.searchProducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}

