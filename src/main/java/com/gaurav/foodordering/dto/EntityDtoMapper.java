package com.gaurav.foodordering.dto;

import com.gaurav.foodordering.entity.Customer;
import com.gaurav.foodordering.entity.FoodOrder;
import com.gaurav.foodordering.entity.OrderItem;
import com.gaurav.foodordering.entity.Payment;
import com.gaurav.foodordering.entity.Product;

public class EntityDtoMapper {

    public static CustomerDTO mapToCustomerDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());
        return dto;
    }

    public static ProductDTO mapToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setSize(product.getSize());
        return dto;
    }

    public static FoodOrderDTO mapToFoodOrderDTO(FoodOrder foodOrder) {
        FoodOrderDTO dto = new FoodOrderDTO();
        dto.setOrderId(foodOrder.getOrderId());
        dto.setOrderDate(foodOrder.getOrderDate());
        dto.setTotalAmount(foodOrder.getTotalAmount());
        dto.setStatus(foodOrder.getStatus());

        if (foodOrder.getCustomer() != null) {
            dto.setCustomerId(foodOrder.getCustomer().getCustomerId());
            dto.setCustomerName(foodOrder.getCustomer().getFirstName() + " " + foodOrder.getCustomer().getLastName());
        }

        return dto;
    }

    public static OrderItemDTO mapToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setOrderItemId(orderItem.getOrderItemId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setSubtotal(orderItem.getSubtotal());

        if (orderItem.getFoodOrder() != null) {
            dto.setOrderId(orderItem.getFoodOrder().getOrderId());
        }

        if (orderItem.getProduct() != null) {
            dto.setProductId(orderItem.getProduct().getProductId());
            dto.setProductName(orderItem.getProduct().getProductName());
        }

        return dto;
    }

    public static PaymentDTO mapToPaymentDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentStatus(payment.getPaymentStatus());

        if (payment.getFoodOrder() != null) {
            dto.setOrderId(payment.getFoodOrder().getOrderId());
        }

        return dto;
    }
}