package com.example.nisum.gatewayserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallBackController {

    @GetMapping("/customer-service")
    public Mono<String> customerServiceFallBack() {
        return Mono.just("Customer service is not available right now. Try again later!");
    }

    @GetMapping("/account-service")
    public Mono<String> accountServiceFallBack() {
        return Mono.just("Account Service is not available right now. Try again later!");
    }
}