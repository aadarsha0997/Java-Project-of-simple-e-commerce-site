package com.Ayush.ecom_project.service;

import com.Ayush.ecom_project.model.Product;
import com.Ayush.ecom_project.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.io.IOException;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    // Get all Products
    public List<Product> getAllProduct(){
        return repo.findAll();
    }



    // Get special product by ID
    public Product getProductById(int id) {

        return  repo.findById(id).orElse(new Product());
    }


// Allow product to add in database with images
    public void addProduct(Product product, MultipartFile imageFile)  throws  IOException{

        // Check is user send image or not
            if (imageFile.isEmpty()){
                throw new RuntimeException("Image is required");
            }

        // checking the content type of image
        String contentType = imageFile.getContentType();

        if (contentType==null||
                (!contentType.equals("image/jpeg") &&
                !contentType.equals("image/png") &&
                !contentType.equals("image/webp"))) {
            throw new RuntimeException("Only JPG, PNG, and WEBP images are allowed");
        }

        String originalFileName=imageFile.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String fileName = UUID.randomUUID() + extension;
        Path uploadPath = Paths.get("uploads");


        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(imageFile.getInputStream(), filePath);

        product.setImageUrl("/uploads/" + fileName);

        repo.save(product);



    }



    public Product updateProduct(int productId, Product product, MultipartFile imageFile) throws IOException {
        Product existingProduct = repo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setId(productId);

        String originalFileName = imageFile.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String fileName = UUID.randomUUID() + extension;

        Path uploadPath = Paths.get("uploads");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(imageFile.getInputStream(), filePath);

        product.setImageUrl("/uploads/" + fileName);

        String oldImageUrl = existingProduct.getImageUrl();

        if (oldImageUrl != null) {
            String oldFileName = oldImageUrl.substring(oldImageUrl.lastIndexOf("/") + 1);
            Path oldImagePath = Paths.get("uploads").resolve(oldFileName);

            Files.deleteIfExists(oldImagePath);
        }

        return repo.save(product);
    }

    public void deleteProduct(int productId) throws IOException {
        Product product=repo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        String imageUrl=product.getImageUrl();
        if(imageUrl!=null){
            Path imagePath = Paths.get("." + imageUrl);

            Files.deleteIfExists(imagePath);
        }

        repo.delete(product);
    }
}
