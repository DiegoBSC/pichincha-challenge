package com.pichincha.transacctions.service.impl;

import com.pichincha.transacctions.dto.AccountDto;
import com.pichincha.transacctions.dto.MovementDto;
import com.pichincha.transacctions.enums.MovementTypeEnum;
import com.pichincha.transacctions.model.Account;
import com.pichincha.transacctions.model.Movement;
import com.pichincha.transacctions.repository.AccountRepository;
import com.pichincha.transacctions.repository.MovementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MovementServiceImplTest {

    @Mock
    private MovementRepository movementRepository;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MovementServiceImpl movementService;

    @Test
    void createMovement_whenMovementTypeIsDebit_thenReturnMovementDto() {
        MovementDto movementDto = getMovementDto();

        Account account = getAccount();

        AccountDto accountDto = getAccountDto();

        movementDto.setAccount(accountDto);
        Movement movement = new Movement();
        movement.setMovementValue(new BigDecimal(-100));
        movement.setBalanceInitial(new BigDecimal(200));
        movement.setBalanceAvailable(new BigDecimal(100));
        movement.setAccount(account);
        movement.setMovementType(MovementTypeEnum.DEBITO);
        movement.setMovementDate(LocalDateTime.now());
        Mockito.when(movementRepository.findFirstByAccountIdOrderByMovementDateDesc(Mockito.any(UUID.class))).thenReturn(Optional.of(movement));
        Mockito.when(movementRepository.save(Mockito.any(Movement.class))).thenReturn(movement);
        Mockito.when(accountRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(account));

        Mockito.when(modelMapper.map(Mockito.any(Movement.class), Mockito.any())).thenReturn(movementDto);

        MovementDto result = movementService.createMovement(movementDto);

        assertEquals(MovementTypeEnum.DEBITO, result.getMovementType());
        assertEquals(new BigDecimal(100), result.getBalanceAvailable());
    }

    private AccountDto getAccountDto(){
        AccountDto accountDto = new AccountDto();
        accountDto.setId(UUID.randomUUID());
        accountDto.setInitialBalance(new BigDecimal(200));
        return accountDto;
    }

    private Account getAccount(){
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setInitialBalance(new BigDecimal(200));
        return account;
    }

    private MovementDto getMovementDto(){
        MovementDto movementDto = new MovementDto();
        movementDto.setMovementType(MovementTypeEnum.DEBITO);
        movementDto.setMovementValue(new BigDecimal(100));
        movementDto.setMovementDate(LocalDateTime.now());
        movementDto.setBalanceAvailable(new BigDecimal(100));
        return movementDto;
    }
}