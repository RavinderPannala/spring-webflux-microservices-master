package com.example.nisum.customerservice.stepVerifier;

import com.example.nisum.customerservice.model.Customer;
import com.example.nisum.customerservice.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void save() {
        Customer customer = getCustomer();
        Mono<Customer> custMono = customerRepository.save(customer);
        StepVerifier.create(custMono).expectSubscription().expectNextCount(1).verifyComplete();
    }

    @Test
    public void findById() {
        Customer customer = getCustomer();
        Mono<Customer> customerMono = customerRepository.save(customer);
        Mono<Customer> customerById = customerRepository.findById(customer.getId());
        Mono<Customer> findMono = Mono.from(customerMono).then(customerById);

        StepVerifier.create(findMono)
                .consumeNextWith(cust -> {
                    assertEquals(cust.getFirstName(), "Ravi");
                    assertEquals(cust.getLastName(), "Pannala");
                    assertNotNull(cust.getAge());

                }).verifyComplete();
    }

    @Test
    public void findByFirstName(){
        Customer customer = getCustomer();
        Mono<Customer> save = customerRepository.deleteAll().then(customerRepository.save(customer));
        Mono<Customer> byFirstName = customerRepository.findByFirstName(customer.getFirstName());
        Mono<Customer> then = Mono.from(save).then(byFirstName);

        StepVerifier.create(then)
                .consumeNextWith(cust->{
                        assertEquals(cust.getFirstName(),customer.getFirstName());
                        assertNotNull(cust.getAge());
                        assertNull(cust.getAccounts());
        }).verifyComplete();
    }

    @Test
    public void findByFirstName_Fail(){
        Customer customer = getCustomer();
        Mono<Customer> save = customerRepository.save(customer);
        Mono<Customer> hello = customerRepository.findByFirstName("Hello");
        Mono<Customer> then = Mono.from(save).then(hello);

        StepVerifier.create(then)
                .expectSubscription()
                .expectNextCount(0)
                .verifyComplete();
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
