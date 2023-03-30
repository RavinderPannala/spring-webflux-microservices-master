package com.example.nisum.customerservice.repository;

import com.example.nisum.customerservice.model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

    Mono<Customer> findByFirstName(String firstName);
}
