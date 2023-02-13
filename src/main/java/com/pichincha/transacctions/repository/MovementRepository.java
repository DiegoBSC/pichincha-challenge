package com.pichincha.transacctions.repository;

import com.pichincha.transacctions.model.Account;
import com.pichincha.transacctions.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovementRepository extends JpaRepository<Movement, UUID> {

    Optional<Movement> findFirstByAccountIdOrderByMovementDateDesc(UUID accountId);

    @Query(value = "select sum(m.movement_value) from movements m " +
            "where m.account_id  = :accountId " +
            "and m.movement_type = 'DEBITO' " +
            "and m.movement_date between :startDate and :endDate", nativeQuery = true)
    BigDecimal totalDebitFromDateRange(UUID accountId, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "select m from Movement m " +
            "where m.account.client.id = :clientId "+
            "and m.movementDate between :startDate and :endDate")
    List<Movement> findMovementsByClientAndDate(UUID clientId, LocalDateTime startDate, LocalDateTime endDate);

}
