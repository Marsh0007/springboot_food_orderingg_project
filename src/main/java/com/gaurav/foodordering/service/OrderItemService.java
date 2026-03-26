package com.gaurav.foodordering.service;

import com.gaurav.foodordering.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem saveOrderItem(OrderItem orderItem);
    List<OrderItem> getAllOrderItems();
    OrderItem getOrderItemById(int id);
    OrderItem updateOrderItem(int id, OrderItem orderItem);
    void deleteOrderItem(int id);

    List<OrderItem> getOrderItemsByOrderId(int orderId);
    List<OrderItem> getOrderItemsByProductId(int productId);
}