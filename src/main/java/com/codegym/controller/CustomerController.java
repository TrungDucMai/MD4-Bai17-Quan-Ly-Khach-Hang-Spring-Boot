package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    ICustomerService customerService;

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;

    }

    @GetMapping("")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/list");
        modelAndView.addObject("customerList", customerService.findAll());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if (!customer.isPresent()) {
            return new ModelAndView("/error.404");
        }
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("customer", customer.get());
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView updateCustomer(@ModelAttribute ("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("message", "Upadate susscessful !");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteForm(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if (!customer.isPresent()) {
            return new ModelAndView("/error.404");
        }
        ModelAndView modelAndView = new ModelAndView("/delete");
        modelAndView.addObject("customer", customer.get());
        return modelAndView;
    }
    @PostMapping("/delete/{id}")
    public ModelAndView deleteCustomer(@ModelAttribute("customer") Customer customer){
        customerService.remove(customer.getId());
        ModelAndView modelAndView = new ModelAndView("redirect:/customer");
        return modelAndView;
    }
}

