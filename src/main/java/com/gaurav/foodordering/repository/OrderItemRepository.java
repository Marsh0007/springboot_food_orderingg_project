package com.gaurav.foodordering.repository;

import com.gaurav.foodordering.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByFoodOrderOrderId(int orderId);

    List<OrderItem> findByProductProductId(int productId);
}