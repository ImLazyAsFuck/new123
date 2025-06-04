package com.bt.service;

import com.bt.entity.Customer;

import java.util.List;

public interface CustomerService{
    List<Customer> findAll();
    Customer findById(int id);
    void save(Customer customer);
    void delete(Customer customer);
    void update(Customer customer);
    List<Customer> findByName(String name);
}
