package com.example.nisum.customerservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    @Transient
    private List<Account> accounts;

    public Customer(List<Account> accounts) {
    }
}
