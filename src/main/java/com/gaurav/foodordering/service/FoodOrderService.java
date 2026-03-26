package com.gaurav.foodordering.service;

import com.gaurav.foodordering.entity.FoodOrder;

import java.util.List;

public interface FoodOrderService {
    FoodOrder saveOrder(FoodOrder foodOrder);
    List<FoodOrder> getAllOrders();
    FoodOrder getOrderById(int id);
    FoodOrder updateOrderStatus(int id, FoodOrder foodOrder);
    void deleteOrder(int id);

    List<FoodOrder> getOrdersByStatus(String status);
    List<FoodOrder> getOrdersByCustomerId(int customerId);
}