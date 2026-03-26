package com.gaurav.foodordering.service.impl;

import com.gaurav.foodordering.entity.Payment;
import com.gaurav.foodordering.exception.ResourceNotFoundException;
import com.gaurav.foodordering.repository.PaymentRepository;
import com.gaurav.foodordering.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPaymentById(int id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    @Override
    public Payment updatePayment(int id, Payment payment) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));

        existingPayment.setPaymentDate(payment.getPaymentDate());
        existingPayment.setAmount(payment.getAmount());
        existingPayment.setPaymentMethod(payment.getPaymentMethod());
        existingPayment.setPaymentStatus(payment.getPaymentStatus());
        existingPayment.setFoodOrder(payment.getFoodOrder());

        return paymentRepository.save(existingPayment);
    }

    @Override
    public void deletePayment(int id) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));

        paymentRepository.delete(existingPayment);
    }

    @Override
    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByPaymentStatus(status);
    }
}