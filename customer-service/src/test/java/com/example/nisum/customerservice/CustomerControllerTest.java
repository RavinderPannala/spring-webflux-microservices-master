package com.example.nisum.customerservice;

import com.example.nisum.customerservice.controller.CustomerController;
import com.example.nisum.customerservice.model.Customer;
import com.example.nisum.customerservice.service.CustomerService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private WebClient.Builder webClient;

    @Test
    @Ignore
    public void save() {
        Customer customer = getCustomer();
        given(customerService.save(customer)).willReturn(Mono.just(customer));

        webTestClient.post()
                .uri("/api/customer/save")
                .body(BodyInserters.fromValue(customer))
                .exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(customer.getFirstName())
                .jsonPath("$.lastName").isEqualTo(customer.getLastName())
                .jsonPath("$.age").isEqualTo(customer.getAge());
    }

    @Test
    public void findById() {

        Customer customer = getCustomer();

        Mockito.when(customerService.findById("1")).thenReturn(Mono.just(customer));

        webTestClient.get()
                .uri("/api/customer/{id}", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.firstName").isEqualTo("Ravi")
                .jsonPath("$.lastName").isNotEmpty();

        Mockito.verify(customerService, Mockito.times(1)).findById("1");
    }

    @Test
    public void findAll() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(Customer.builder().id("cust_200").age(20).firstName("Ravinder").lastName("Pannala").build());
        customerList.add(Customer.builder().id("cust_201").age(20).firstName("Ayaan").lastName("Pannala").build());
        customerList.add(Customer.builder().id("cust_202").age(20).firstName("Lavanya").lastName("Pannala").build());

        Flux<Customer> customerFlux = Flux.fromIterable(customerList);

        Mockito.when(customerService.findAll()).thenReturn(customerFlux);

        webTestClient.get()
                .uri("/api/customer/")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Customer.class)
                .consumeWith(System.out::println);
    }

    @Test
    public void update() {
        Customer customer = getCustomer();

        Customer cust = Customer.builder().lastName("Hello").firstName("Ravinder").age(29).id("1").build();

        Mockito.when(customerService.update(cust,customer.getId())).thenReturn(Mono.just(cust));

        webTestClient.put().uri("/api/customer/update/{id}", customer.getId())
                .body(BodyInserters.fromValue(cust))
                .exchange().expectStatus().isOk()
                .expectBody()
                .jsonPath("$.firstName").isEqualTo(cust.getFirstName())
                .consumeWith(System.out::println);

        Mockito.verify(customerService,times(1)).update(cust,customer.getId());
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
