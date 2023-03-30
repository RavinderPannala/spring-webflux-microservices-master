package com.example.nisum.customerservice.model;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String id;
    private String number;
    private int amount;


}
