package com.gaurav.foodordering.service.impl;

import com.gaurav.foodordering.entity.FoodOrder;
import com.gaurav.foodordering.entity.OrderItem;
import com.gaurav.foodordering.entity.Product;
import com.gaurav.foodordering.exception.InsufficientStockException;
import com.gaurav.foodordering.exception.ResourceNotFoundException;
import com.gaurav.foodordering.repository.FoodOrderRepository;
import com.gaurav.foodordering.repository.OrderItemRepository;
import com.gaurav.foodordering.repository.ProductRepository;
import com.gaurav.foodordering.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final FoodOrderRepository foodOrderRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository,
                                ProductRepository productRepository,
                                FoodOrderRepository foodOrderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.foodOrderRepository = foodOrderRepository;
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        int productId = orderItem.getProduct().getProductId();
        int orderId = orderItem.getFoodOrder().getOrderId();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        FoodOrder foodOrder = foodOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        int requestedQuantity = orderItem.getQuantity();

        if (product.getStock() < requestedQuantity) {
            throw new InsufficientStockException(
                    "Not enough stock for product: " + product.getProductName() +
                            ". Available stock: " + product.getStock()
            );
        }

        double subtotal = product.getPrice() * requestedQuantity;

        product.setStock(product.getStock() - requestedQuantity);
        productRepository.save(product);

        orderItem.setProduct(product);
        orderItem.setFoodOrder(foodOrder);
        orderItem.setSubtotal(subtotal);

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        updateOrderTotal(orderId);

        return savedOrderItem;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem getOrderItemById(int id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + id));
    }

    @Override
    public OrderItem updateOrderItem(int id, OrderItem orderItem) {
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + id));

        Product oldProduct = existingOrderItem.getProduct();
        oldProduct.setStock(oldProduct.getStock() + existingOrderItem.getQuantity());
        productRepository.save(oldProduct);

        int newProductId = orderItem.getProduct().getProductId();
        int orderId = orderItem.getFoodOrder().getOrderId();

        Product newProduct = productRepository.findById(newProductId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + newProductId));

        FoodOrder foodOrder = foodOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        int requestedQuantity = orderItem.getQuantity();

        if (newProduct.getStock() < requestedQuantity) {
            throw new InsufficientStockException(
                    "Not enough stock for product: " + newProduct.getProductName() +
                            ". Available stock: " + newProduct.getStock()
            );
        }

        double subtotal = newProduct.getPrice() * requestedQuantity;

        newProduct.setStock(newProduct.getStock() - requestedQuantity);
        productRepository.save(newProduct);

        existingOrderItem.setQuantity(requestedQuantity);
        existingOrderItem.setProduct(newProduct);
        existingOrderItem.setFoodOrder(foodOrder);
        existingOrderItem.setSubtotal(subtotal);

        OrderItem updatedOrderItem = orderItemRepository.save(existingOrderItem);

        updateOrderTotal(orderId);

        return updatedOrderItem;
    }

    @Override
    public void deleteOrderItem(int id) {
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + id));

        Product product = existingOrderItem.getProduct();
        product.setStock(product.getStock() + existingOrderItem.getQuantity());
        productRepository.save(product);

        int orderId = existingOrderItem.getFoodOrder().getOrderId();

        orderItemRepository.delete(existingOrderItem);

        updateOrderTotal(orderId);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        return orderItemRepository.findByFoodOrderOrderId(orderId);
    }

    @Override
    public List<OrderItem> getOrderItemsByProductId(int productId) {
        return orderItemRepository.findByProductProductId(productId);
    }

    private void updateOrderTotal(int orderId) {
        FoodOrder foodOrder = foodOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        List<OrderItem> orderItems = orderItemRepository.findByFoodOrderOrderId(orderId);

        double total = 0;
        for (OrderItem item : orderItems) {
            total += item.getSubtotal();
        }

        foodOrder.setTotalAmount(total);
        foodOrderRepository.save(foodOrder);
    }
}