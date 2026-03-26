package com.gaurav.foodordering.service;

import com.gaurav.foodordering.entity.Payment;

import java.util.List;

public interface PaymentService {
    Payment savePayment(Payment payment);
    List<Payment> getAllPayments();
    Payment getPaymentById(int id);
    Payment updatePayment(int id, Payment payment);
    void deletePayment(int id);

    List<Payment> getPaymentsByStatus(String status);
}