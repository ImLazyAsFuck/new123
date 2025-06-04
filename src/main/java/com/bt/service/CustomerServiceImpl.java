package com.bt.service;

import com.bt.entity.Customer;
import com.bt.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements  CustomerService{
    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public List<Customer> findAll(){
        return customerRepo.findAll();
    }

    @Override
    public Customer findById(long id){
        return customerRepo.findById(id);
    }

    @Override
    public void save(Customer customer){
        customerRepo.save(customer);
    }

    @Override
    public void delete(Customer customer){
        customerRepo.delete(customer);
    }

    @Override
    public void update(Customer customer){
        customerRepo.update(customer);
    }

    @Override
    public List<Customer> findByName(String name){
        return customerRepo.findByName(name);
    }
}
