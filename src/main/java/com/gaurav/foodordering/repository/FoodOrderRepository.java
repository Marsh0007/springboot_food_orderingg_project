package com.gaurav.foodordering.repository;

import com.gaurav.foodordering.entity.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Integer> {

    List<FoodOrder> findByStatus(String status);

    List<FoodOrder> findByCustomerCustomerId(int customerId);
}