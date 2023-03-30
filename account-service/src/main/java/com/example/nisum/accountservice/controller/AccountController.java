package com.example.nisum.accountservice.controller;

import com.example.nisum.accountservice.exception.ResourceNotFoundException;
import com.example.nisum.accountservice.model.Account;
import com.example.nisum.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/save")
    public Mono<Account> save(@RequestBody Account account) {
        return accountService.save(account);
    }

    @GetMapping("/")
    public Flux<Account> findAll() {
        return accountService.findAll()
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("There is not account")));
    }

    @GetMapping("/{id}")
    public Mono<Account> findById(@PathVariable String id) {
        return accountService.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Account is not found with id" + id)));
    }

    @GetMapping("/customer/{customerId}")
    public Flux<Account> findByCustomer(@PathVariable String customerId) {
        return accountService.findByCustomerId(customerId)
                .switchIfEmpty((Flux.error(new ResourceNotFoundException("customer doesn't have any account" + customerId))));
    }

}
