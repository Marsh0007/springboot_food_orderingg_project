package com.gaurav.foodordering.service;

import com.gaurav.foodordering.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(int id);
    Customer updateCustomer(int id, Customer customer);
    void deleteCustomer(int id);
}