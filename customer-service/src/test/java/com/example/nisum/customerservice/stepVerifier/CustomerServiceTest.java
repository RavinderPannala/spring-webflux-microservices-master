package com.example.nisum.customerservice.stepVerifier;

import com.example.nisum.customerservice.exception.ResourceNotFoundException;
import com.example.nisum.customerservice.model.Customer;
import com.example.nisum.customerservice.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    @MockBean
    CustomerRepository customerRepository;


    @Test
    public void findById() {
        Customer customer = getCustomer();
        Mockito.when(customerRepository.findById(customer.getId())).thenReturn(Mono.just(customer));
        Mono<Customer> byId = customerRepository.findById(customer.getId());
        StepVerifier.
                create(byId).
                consumeNextWith(cust -> {
                    assertEquals(cust.getFirstName(), customer.getFirstName());
                }).verifyComplete();
    }

    @Test
    public void findById_NotFound() {
        Customer customer = getCustomer();
        Mockito.when(customerRepository.findByFirstName("Pannala")).thenReturn(Mono.empty());
        Mono<Customer> byId = customerRepository.findByFirstName("Pannala");
        StepVerifier.create(byId).expectError()
                //.expectErrorMatches(throwable -> throwable instanceof ResourceNotFoundException)
                .verify();

    }

    public Customer getCustomer() {
        Customer customer = new Customer();
        customer.setId("1");
        customer.setAge(20);
        customer.setFirstName("Ravi");
        customer.setLastName("Pannala");
        return customer;
    }


}
