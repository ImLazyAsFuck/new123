package com.bt.controller;

import com.bt.dto.CustomerDto;
import com.bt.entity.Customer;
import com.bt.service.CustomerService;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController{

    @Autowired
    private Cloudinary cloudinary;

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
        model.addAttribute("customerDto", new CustomerDto());
        return "add_customer";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable long id){
        Customer customer = customerService.findById(id);
        if(customer == null){
            return "redirect:/customers/";
        }
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setAddress(customer.getAddress());
        customerDto.setPhone(customer.getPhone());
        customerDto.setImage(customer.getImage());
        model.addAttribute("customerDto", customerDto);
        return "edit_customer";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("customerDto") CustomerDto customerDto, BindingResult bindingResult) throws IOException{
        if(bindingResult.hasErrors()){
            return "add_customer";
        }
        if(customerDto.getFile() != null && !customerDto.getFile().isEmpty()){
            String url = cloudinary.uploader().upload(customerDto.getFile().getBytes(), null).get("url").toString();
            customerDto.setImage(url);
        }
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setAddress(customerDto.getAddress());
        customer.setPhone(customerDto.getPhone());

        customerService.save(customer);
        return "redirect:/customers/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id){
        Customer customer = customerService.findById(id);
        if(customer != null){
            customerService.delete(customer);
        }
        return "redirect:/customers/";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("customerDto") CustomerDto customerDto, BindingResult bindingResult, @PathVariable long id) throws IOException{
        if(bindingResult.hasErrors()){
            return "edit_customer";
        }
        Customer foundCustomer = customerService.findById(id);
        if(customerDto.getFile() != null && !customerDto.getFile().isEmpty()){
            String url = cloudinary.uploader().upload(customerDto.getFile().getBytes(), null).get("url").toString();
            customerDto.setImage(url);
        }else{
            customerDto.setImage(foundCustomer.getImage());
        }
        if(foundCustomer != null){
            foundCustomer.setFirstName(customerDto.getFirstName());
            foundCustomer.setLastName(customerDto.getLastName());
            foundCustomer.setAddress(customerDto.getAddress());
            foundCustomer.setPhone(customerDto.getPhone());
            foundCustomer.setImage(customerDto.getImage());
            customerService.update(foundCustomer);
        }
        return "redirect:/customers/";
    }
}
