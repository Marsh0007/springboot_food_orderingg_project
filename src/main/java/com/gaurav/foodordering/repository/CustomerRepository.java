package com.gaurav.foodordering.repository;

import com.gaurav.foodordering.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}