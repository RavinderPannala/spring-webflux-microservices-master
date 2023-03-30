package com.example.nisum.customerservice.utils;

import com.example.nisum.customerservice.model.Customer;

import java.util.List;

public class CustomerUtils {

    public static Customer mapper(List<Customer> customerList) {
        Customer newCustomer = new Customer();
        for (Customer customer : customerList) {
            if (customer.getFirstName() != null) newCustomer.setFirstName(customer.getFirstName());
            if (customer.getLastName() != null) newCustomer.setLastName(customer.getLastName());
            if (customer.getAge() != 0) newCustomer.setAge(customer.getAge());
            if (customer.getAccounts() != null) newCustomer.setAccounts(customer.getAccounts());
        }
        return newCustomer;
    }
}
