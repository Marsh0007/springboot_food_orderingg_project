package com.gaurav.foodordering.controller;

import com.gaurav.foodordering.dto.EntityDtoMapper;
import com.gaurav.foodordering.dto.PaymentDTO;
import com.gaurav.foodordering.entity.Payment;
import com.gaurav.foodordering.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public PaymentDTO savePayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.savePayment(payment);
        return EntityDtoMapper.mapToPaymentDTO(savedPayment);
    }

    @GetMapping
    public List<PaymentDTO> getAllPayments() {
        return paymentService.getAllPayments()
                .stream()
                .map(EntityDtoMapper::mapToPaymentDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PaymentDTO getPaymentById(@PathVariable int id) {
        Payment payment = paymentService.getPaymentById(id);
        return EntityDtoMapper.mapToPaymentDTO(payment);
    }

    @PutMapping("/{id}")
    public PaymentDTO updatePayment(@PathVariable int id, @RequestBody Payment payment) {
        Payment updatedPayment = paymentService.updatePayment(id, payment);
        return EntityDtoMapper.mapToPaymentDTO(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public String deletePayment(@PathVariable int id) {
        paymentService.deletePayment(id);
        return "Payment deleted successfully with id: " + id;
    }

    @GetMapping("/status/{status}")
    public List<PaymentDTO> getPaymentsByStatus(@PathVariable String status) {
        return paymentService.getPaymentsByStatus(status)
                .stream()
                .map(EntityDtoMapper::mapToPaymentDTO)
                .collect(Collectors.toList());
    }
}