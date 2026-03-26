package com.gaurav.foodordering.service;

import com.gaurav.foodordering.entity.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(int id);
    Product updateProduct(int id, Product product);
    void deleteProduct(int id);

    List<Product> getProductsByCategory(String category);
    List<Product> getLowStockProducts(int stock);
}