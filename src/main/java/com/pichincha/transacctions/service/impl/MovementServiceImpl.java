package com.pichincha.transacctions.service.impl;

import com.pichincha.transacctions.dto.MovementDto;
import com.pichincha.transacctions.enums.MovementTypeEnum;
import com.pichincha.transacctions.model.Account;
import com.pichincha.transacctions.model.Movement;
import com.pichincha.transacctions.repository.AccountRepository;
import com.pichincha.transacctions.repository.MovementRepository;
import com.pichincha.transacctions.service.MovementService;
import com.pichincha.transacctions.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;

    private final AccountRepository accountRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<MovementDto> indexMovement() {
        return movementRepository.findAll().stream()
                .map(movement -> modelMapper.map(movement, MovementDto.class)
        ).toList();
    }

    @Override
    public MovementDto createMovement(MovementDto movementDto) {
        validateMovementAmount(movementDto.getMovementValue());
        Account account = findAccount(movementDto.getAccount().getId());

        Optional<Movement> latestMovement = movementRepository.findFirstByAccountIdOrderByMovementDateDesc(movementDto.getAccount().getId());

        BigDecimal actualBalance = latestMovement.isPresent() ? latestMovement.get().getBalanceAvailable() : account.getInitialBalance();

        Movement movement = new Movement();
        movement.setBalanceInitial(actualBalance);

        switch (movementDto.getMovementType()) {
            case DEBITO -> {
                if (actualBalance.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error: Saldo no disponible");
                }
                if (movementDto.getMovementValue().compareTo(actualBalance) > 0) {
                    throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error: El valor del retiro es mayor al saldo disponible");
                }
                if(exceedDailyQuota(account.getId())){
                    throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error: Cupo diario Excedido");
                }
                movement.setMovementValue(movementDto.getMovementValue().negate());
                movement.setBalanceAvailable(actualBalance.subtract(movementDto.getMovementValue()).setScale(2, RoundingMode.HALF_UP));
            }
            case CREDITO -> {
                movement.setMovementValue(movementDto.getMovementValue());
                movement.setBalanceAvailable(actualBalance.add(movementDto.getMovementValue()).setScale(2, RoundingMode.HALF_UP));
            }
        }
        movement.setAccount(account);
        movement.setMovementType(movementDto.getMovementType());
        movement.setMovementDate(LocalDateTime.now());
        return modelMapper.map(movementRepository.save(movement), MovementDto.class);
    }


    @Override
    public void deleteMovement(UUID movementId) {
        Movement movement = movementRepository.findById(movementId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error: No existe el movimiento"));

        Account account = findAccount(movement.getAccount().getId());

        Optional<Movement> latestMovement = movementRepository.findFirstByAccountIdOrderByMovementDateDesc(account.getId());

        BigDecimal actualBalance = latestMovement.isPresent() ? latestMovement.get().getBalanceAvailable() : account.getInitialBalance();

        Movement movementReverse = new Movement();
        movementReverse.setBalanceInitial(actualBalance);
        movementReverse.setMovementType(MovementTypeEnum.REVERSO);
        movementReverse.setAccount(account);
        movementReverse.setMovementDate(LocalDateTime.now());

        switch (movement.getMovementType()) {
            case DEBITO -> {
                movementReverse.setMovementValue(movement.getMovementValue().abs());
                movementReverse.setBalanceAvailable(actualBalance.add(movement.getMovementValue().abs()).setScale(2, RoundingMode.HALF_UP));
            }
            case CREDITO -> {
                movement.setMovementValue(movement.getMovementValue().negate());
                movement.setBalanceAvailable(actualBalance.subtract(movement.getMovementValue()).setScale(2, RoundingMode.HALF_UP));
            }
        }
        movementRepository.save(movementReverse);
    }

    @Override
    public MovementDto showMovementById(UUID movementId) {
        Movement movement = movementRepository.findById(movementId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error: No existe el movimiento"));
        return modelMapper.map(movement, MovementDto.class);
    }

    private void validateMovementAmount(BigDecimal movementAmount) {
        if (movementAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error: El valor de la transaccion no puede ser 0");
        }
    }

    private Account findAccount(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error: No existe la cuenta"));
    }

    private boolean exceedDailyQuota(UUID accountId){
        BigDecimal totalQuote =  movementRepository
                .totalDebitFromDateRange(accountId, DateUtils.setTimeFromLocalNowStart(LocalDateTime.now()),
                        DateUtils.setTimeFromLocalNowEnd(LocalDateTime.now()));

        if(totalQuote != null)
            return totalQuote.abs().compareTo(new BigDecimal(1000)) > 0;

        return false;
    }
}
