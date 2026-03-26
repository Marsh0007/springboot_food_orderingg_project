package com.gaurav.foodordering.controller;

import com.gaurav.foodordering.dto.CustomerDTO;
import com.gaurav.foodordering.dto.EntityDtoMapper;
import com.gaurav.foodordering.entity.Customer;
import com.gaurav.foodordering.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);
        return EntityDtoMapper.mapToCustomerDTO(savedCustomer);
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(EntityDtoMapper::mapToCustomerDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable int id) {
        Customer customer = customerService.getCustomerById(id);
        return EntityDtoMapper.mapToCustomerDTO(customer);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return EntityDtoMapper.mapToCustomerDTO(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
        return "Customer deleted successfully with id: " + id;
    }
}