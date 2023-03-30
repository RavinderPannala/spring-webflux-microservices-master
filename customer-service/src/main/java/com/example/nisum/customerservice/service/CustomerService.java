package com.example.nisum.customerservice.service;


import com.example.nisum.customerservice.exception.ResourceNotFoundException;
import com.example.nisum.customerservice.model.Account;
import com.example.nisum.customerservice.model.Customer;
import com.example.nisum.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WebClient.Builder webClient;

    public Mono<Customer> findById(String id) {
        return customerRepository.findById(id);
    }

    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Mono<Customer> findIdWithAccount(String customerId) {
        Flux<Account> accountFlux = webClient.build()
                .get()
                .uri("/api/account/customer/{customerId}", customerId)
                .retrieve()
                .bodyToFlux(Account.class)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Accounts are not available with id" + customerId)));
        Mono<Customer> map = accountFlux.collectList()
                .zipWith(customerRepository.findById(customerId))
                .map(tuple2 -> {
                    Customer customer = tuple2.getT2();
                    customer.setAccounts(tuple2.getT1());
                    return customer;
                })
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("customers are not available with id" + customerId)));
        return map;
    }

    public Mono<Customer> findIdWithAccount1(String customerId) {
        Flux<Account> accountFlux = webClient.build()
                .get()
                .uri("/api/account/customer/{customerId}", customerId)
                .headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .exchangeToFlux(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToFlux(Account.class);
                    } else if (clientResponse.statusCode().is4xxClientError() || clientResponse.statusCode().is5xxServerError()) {
                        return Flux.empty();
                    } else {
                        return Flux.empty();
                    }
                })
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Accounts are not available with id" + customerId)));
        Mono<Customer> map = accountFlux.collectList()
                .zipWith(customerRepository.findById(customerId))
                .map(tuple2 -> {
                    Customer customer = tuple2.getT2();
                    customer.setAccounts(tuple2.getT1());
                    return customer;
                })
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("customers are not available with id" + customerId)));
        return map;
    }

    public Mono<Customer> save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Mono<Customer> update(Customer customer, String id) {
        Mono<Customer> customerMono = customerRepository.findById(id).flatMap(cust -> {
            cust.setLastName(customer.getLastName());
            cust.setFirstName(customer.getFirstName());
            cust.setAge(customer.getAge());
            cust.setId(cust.getId());
            return customerRepository.save(cust);
        });
        return customerMono;
    }

}
