package com.pichincha.transacctions.model;

import com.pichincha.transacctions.enums.MovementTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "movements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "movement_date")
    private LocalDateTime movementDate;

    @Column(name = "movement_type")
    @Enumerated(EnumType.STRING)
    private MovementTypeEnum movementType;

    @Column(name = "movement_value")
    private BigDecimal movementValue;

    @Column(name = "balance_available")
    private BigDecimal balanceAvailable;

    @Column(name = "balance_initial")
    private BigDecimal balanceInitial;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
