package com.example.nisum.customerservice.controller;


import com.example.nisum.customerservice.exception.ResourceNotFoundException;
import com.example.nisum.customerservice.model.Customer;
import com.example.nisum.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public Mono<Customer> findById(@PathVariable String id) {
        return customerService.findById(id).switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer not availabel with id" + id)));
    }

    @GetMapping("/")
    public Flux<Customer> findAll() {
        return customerService.findAll().switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer not available ")));
    }

    @GetMapping("/{id}/with-accounts")
    public Mono<Customer> findIdWithAccounts(@PathVariable String id) {
        return customerService.findIdWithAccount(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Customer not available with id" + id)));
    }

    @PostMapping("/save")
    public Mono<Customer> save(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @PutMapping("/update/{id}")
    public Mono<Customer> update(@RequestBody Customer customer, @PathVariable String id) {
        return customerService.update(customer, id);
    }

}
