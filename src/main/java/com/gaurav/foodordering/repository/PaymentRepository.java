package com.gaurav.foodordering.repository;

import com.gaurav.foodordering.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findByPaymentStatus(String paymentStatus);
}