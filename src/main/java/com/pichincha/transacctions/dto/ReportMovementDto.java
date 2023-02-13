package com.pichincha.transacctions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pichincha.transacctions.enums.AccountTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportMovementDto {

    @JsonProperty("Fecha")
    private LocalDateTime transactionDate;
    @JsonProperty("Cliente")
    private String clientName;
    @JsonProperty("Numero Cuenta")
    private String accountNumber;
    @JsonProperty("Tipo")
    private AccountTypeEnum accountType;
    @JsonProperty("Saldo inicial")
    private BigDecimal initialBalance;
    @JsonProperty("Movimiento")
    private BigDecimal movementValue;
    @JsonProperty("Saldo Disponible")
    private BigDecimal balanceAvailable;
    @JsonProperty("Estado")
    private Boolean state;

}
