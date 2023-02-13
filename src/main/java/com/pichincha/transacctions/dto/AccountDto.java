package com.pichincha.transacctions.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pichincha.transacctions.enums.AccountTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private UUID id;

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountTypeEnum type;

    private BigDecimal initialBalance;

    private Boolean state;

    private ClientDto client;

    @JsonIgnore
    private Set<MovementDto> movements;
}
