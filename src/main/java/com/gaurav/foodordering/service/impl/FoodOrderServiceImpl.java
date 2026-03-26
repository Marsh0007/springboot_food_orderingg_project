package com.gaurav.foodordering.service.impl;

import com.gaurav.foodordering.entity.FoodOrder;
import com.gaurav.foodordering.exception.ResourceNotFoundException;
import com.gaurav.foodordering.repository.FoodOrderRepository;
import com.gaurav.foodordering.service.FoodOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodOrderServiceImpl implements FoodOrderService {

    private final FoodOrderRepository foodOrderRepository;

    public FoodOrderServiceImpl(FoodOrderRepository foodOrderRepository) {
        this.foodOrderRepository = foodOrderRepository;
    }

    @Override
    public FoodOrder saveOrder(FoodOrder foodOrder) {
        foodOrder.setTotalAmount(0);
        return foodOrderRepository.save(foodOrder);
    }

    @Override
    public List<FoodOrder> getAllOrders() {
        return foodOrderRepository.findAll();
    }

    @Override
    public FoodOrder getOrderById(int id) {
        return foodOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Override
    public FoodOrder updateOrderStatus(int id, FoodOrder foodOrder) {
        FoodOrder existingOrder = foodOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        existingOrder.setOrderDate(foodOrder.getOrderDate());
        existingOrder.setTotalAmount(foodOrder.getTotalAmount());
        existingOrder.setStatus(foodOrder.getStatus());
        existingOrder.setCustomer(foodOrder.getCustomer());

        return foodOrderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(int id) {
        FoodOrder existingOrder = foodOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        foodOrderRepository.delete(existingOrder);
    }

    @Override
    public List<FoodOrder> getOrdersByStatus(String status) {
        return foodOrderRepository.findByStatus(status);
    }

    @Override
    public List<FoodOrder> getOrdersByCustomerId(int customerId) {
        return foodOrderRepository.findByCustomerCustomerId(customerId);
    }
}