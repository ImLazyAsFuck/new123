package com.bt.controller;

import com.bt.dto.CustomerDto;
import com.bt.entity.Customer;
import com.bt.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController{

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String findAll(@RequestParam(required = false) String search, Model model){
        if(search == null){
            model.addAttribute("customers", customerService.findAll());
        }else{
            model.addAttribute("customers", customerService.findByName(search));
        }
        return "customer_list";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("customer", new CustomerDto());
        return "add_customer";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id){
        Customer customer = customerService.findById(id);
        if(customer != null){
            return "redirect:/customers/";
        }
        model.addAttribute("customer", customer);
        return "edit_customer";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "add_customer";
        }
        customerService.save(customer);
        return "redirect:/customers/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        Customer customer = customerService.findById(id);
        if(customer != null){
            customerService.delete(customer);
        }
        return "redirect:/customers/";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult, @PathVariable Integer id){
        if(bindingResult.hasErrors()){
            return "add_customer";
        }
        Customer foundCustomer = customerService.findById(id);
        if(foundCustomer != null){
            customerService.update(customer);
        }
        return "redirect:/customers/";
    }
}
