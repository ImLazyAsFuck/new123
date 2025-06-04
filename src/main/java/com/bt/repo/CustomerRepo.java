package com.bt.repo;

import com.bt.entity.Customer;

import java.util.List;

public interface CustomerRepo{
    List<Customer> findAll();
    Customer findById(long id);
    void save(Customer customer);
    void delete(Customer customer);
    void update(Customer customer);
    List<Customer> findByName(String name);
}
