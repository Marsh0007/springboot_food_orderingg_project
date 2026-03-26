package com.gaurav.foodordering.controller;

import com.gaurav.foodordering.dto.EntityDtoMapper;
import com.gaurav.foodordering.dto.ProductDTO;
import com.gaurav.foodordering.entity.Product;
import com.gaurav.foodordering.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductDTO saveProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return EntityDtoMapper.mapToProductDTO(savedProduct);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts()
                .stream()
                .map(EntityDtoMapper::mapToProductDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        return EntityDtoMapper.mapToProductDTO(product);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable int id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return EntityDtoMapper.mapToProductDTO(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return "Product deleted successfully with id: " + id;
    }

    @GetMapping("/category/{category}")
    public List<ProductDTO> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category)
                .stream()
                .map(EntityDtoMapper::mapToProductDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/low-stock/{stock}")
    public List<ProductDTO> getLowStockProducts(@PathVariable int stock) {
        return productService.getLowStockProducts(stock)
                .stream()
                .map(EntityDtoMapper::mapToProductDTO)
                .collect(Collectors.toList());
    }
}