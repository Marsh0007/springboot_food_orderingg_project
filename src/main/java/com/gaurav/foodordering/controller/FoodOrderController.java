package com.gaurav.foodordering.controller;

import com.gaurav.foodordering.dto.EntityDtoMapper;
import com.gaurav.foodordering.dto.FoodOrderDTO;
import com.gaurav.foodordering.entity.FoodOrder;
import com.gaurav.foodordering.service.FoodOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class FoodOrderController {

    private final FoodOrderService foodOrderService;

    public FoodOrderController(FoodOrderService foodOrderService) {
        this.foodOrderService = foodOrderService;
    }

    @PostMapping
    public FoodOrderDTO saveOrder(@RequestBody FoodOrder foodOrder) {
        FoodOrder savedOrder = foodOrderService.saveOrder(foodOrder);
        return EntityDtoMapper.mapToFoodOrderDTO(savedOrder);
    }

    @GetMapping
    public List<FoodOrderDTO> getAllOrders() {
        return foodOrderService.getAllOrders()
                .stream()
                .map(EntityDtoMapper::mapToFoodOrderDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public FoodOrderDTO getOrderById(@PathVariable int id) {
        FoodOrder order = foodOrderService.getOrderById(id);
        return EntityDtoMapper.mapToFoodOrderDTO(order);
    }

    @PutMapping("/{id}")
    public FoodOrderDTO updateOrder(@PathVariable int id, @RequestBody FoodOrder foodOrder) {
        FoodOrder updatedOrder = foodOrderService.updateOrderStatus(id, foodOrder);
        return EntityDtoMapper.mapToFoodOrderDTO(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable int id) {
        foodOrderService.deleteOrder(id);
        return "Order deleted successfully with id: " + id;
    }

    @GetMapping("/status/{status}")
    public List<FoodOrderDTO> getOrdersByStatus(@PathVariable String status) {
        return foodOrderService.getOrdersByStatus(status)
                .stream()
                .map(EntityDtoMapper::mapToFoodOrderDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<FoodOrderDTO> getOrdersByCustomerId(@PathVariable int customerId) {
        return foodOrderService.getOrdersByCustomerId(customerId)
                .stream()
                .map(EntityDtoMapper::mapToFoodOrderDTO)
                .collect(Collectors.toList());
    }
}