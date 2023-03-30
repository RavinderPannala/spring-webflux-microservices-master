package com.example.nisum.accountservice.service;

import com.example.nisum.accountservice.model.Account;
import com.example.nisum.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Mono<Account> save(Account account) {
        return accountRepository.save(account);
    }

    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

    public Mono<Account> findById(String id) {
        return accountRepository.findById(id);
    }

    public Flux<Account> findByCustomerId(String customerId) {
        return accountRepository.findByCustomerId(customerId);
    }
}
