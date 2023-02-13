package com.pichincha.transacctions.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pichincha.transacctions.enums.MovementTypeEnum;
import com.pichincha.transacctions.model.Account;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementDto {
    private UUID id;

    private LocalDateTime movementDate;

    @Enumerated(EnumType.STRING)
    private MovementTypeEnum movementType;

    private BigDecimal movementValue;
    private BigDecimal balanceAvailable;
    private BigDecimal balanceInitial;

    private AccountDto account;
}
