package it.poste.bank.service.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Builder
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String accountNumber;
    private String branch;
    private String email;
}
