package com.example.nisum.accountservice;

import com.example.nisum.accountservice.controller.AccountController;
import com.example.nisum.accountservice.model.Account;
import com.example.nisum.accountservice.repository.AccountRepository;
import com.example.nisum.accountservice.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = AccountController.class)
@Import(AccountService.class)
public class AccountControllerTest {

    @MockBean
    AccountRepository accountRepository;

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void save() {
        Account account = new Account();
        account.setAmount(1000);
        account.setNumber("IAF2332");
        account.setCustomerId("CST_001");

        Mockito.when(accountRepository.save(account)).thenReturn(Mono.just(account));
        webTestClient.post()
                .uri("/api/account/save")
                .body(Mono.just(account), Account.class)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(accountRepository, Mockito.times(1)).save(account);
    }

    @Test
    public void findById() {
        Account account = new Account();
        account.setId("100");
        account.setCustomerId("Cust_100");
        account.setNumber("234RTD");
        account.setAmount(1000);

        Mockito.when(accountRepository.findById("100")).thenReturn(Mono.just(account));

        webTestClient.get()
                .uri("/api/account/{id}", "100")
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(accountRepository, Mockito.times(1)).findById("100");

    }
}
