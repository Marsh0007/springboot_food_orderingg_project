package com.gaurav.foodordering.controller;

import com.gaurav.foodordering.dto.EntityDtoMapper;
import com.gaurav.foodordering.dto.OrderItemDTO;
import com.gaurav.foodordering.entity.OrderItem;
import com.gaurav.foodordering.service.OrderItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order-items")
@CrossOrigin(origins = "*")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public OrderItemDTO saveOrderItem(@RequestBody OrderItem orderItem) {
        OrderItem savedOrderItem = orderItemService.saveOrderItem(orderItem);
        return EntityDtoMapper.mapToOrderItemDTO(savedOrderItem);
    }

    @GetMapping
    public List<OrderItemDTO> getAllOrderItems() {
        return orderItemService.getAllOrderItems()
                .stream()
                .map(EntityDtoMapper::mapToOrderItemDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderItemDTO getOrderItemById(@PathVariable int id) {
        OrderItem orderItem = orderItemService.getOrderItemById(id);
        return EntityDtoMapper.mapToOrderItemDTO(orderItem);
    }

    @PutMapping("/{id}")
    public OrderItemDTO updateOrderItem(@PathVariable int id, @RequestBody OrderItem orderItem) {
        OrderItem updatedOrderItem = orderItemService.updateOrderItem(id, orderItem);
        return EntityDtoMapper.mapToOrderItemDTO(updatedOrderItem);
    }

    @DeleteMapping("/{id}")
    public String deleteOrderItem(@PathVariable int id) {
        orderItemService.deleteOrderItem(id);
        return "Order item deleted successfully with id: " + id;
    }

    @GetMapping("/order/{orderId}")
    public List<OrderItemDTO> getOrderItemsByOrderId(@PathVariable int orderId) {
        return orderItemService.getOrderItemsByOrderId(orderId)
                .stream()
                .map(EntityDtoMapper::mapToOrderItemDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/product/{productId}")
    public List<OrderItemDTO> getOrderItemsByProductId(@PathVariable int productId) {
        return orderItemService.getOrderItemsByProductId(productId)
                .stream()
                .map(EntityDtoMapper::mapToOrderItemDTO)
                .collect(Collectors.toList());
    }
}