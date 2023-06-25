package com.lidor.coupon.controllers;


import com.lidor.coupon.dto.CustomerData;
import com.lidor.coupon.entities.Customer;
import com.lidor.coupon.enums.UserType;
import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.logic.CustomersLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomersLogic customersLogic;

    @Autowired
    public CustomerController(CustomersLogic customersLogic) {
        this.customersLogic = customersLogic;
    }

    @PostMapping
    public void createCustomer(@RequestBody Customer customer) throws ServerException {
        customer.getUser().setUserType(UserType.Customer);
        customersLogic.addCustomer(customer);
    }

    @PutMapping
    public void updateCustomer(@RequestBody Customer customer) throws ServerException {
        customersLogic.updateCustomer(customer);
    }

    @DeleteMapping("{customerId}")
    public void removeCustomer(@PathVariable("customerId") long customerId) throws ServerException {
        customersLogic.removeCustomer(customerId);
    }

    @GetMapping("{customerId}")
    public Customer getCustomer(@PathVariable("customerId") int customerId) throws ServerException {
        return customersLogic.getCustomer(customerId);
    }

    @GetMapping
    public List<CustomerData> getCustomers(@RequestParam("pageNumber") int pageNumber) throws ServerException {
        return customersLogic.getCustomers(pageNumber);
    }

}
