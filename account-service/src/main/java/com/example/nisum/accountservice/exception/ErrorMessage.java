package com.example.nisum.accountservice.exception;

import lombok.*;

import java.time.LocalDate;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ErrorMessage {

    private String errorMessage;
    private String errorDescription;
    private int errorStatusCode;
    private LocalDate errorDate;
}
