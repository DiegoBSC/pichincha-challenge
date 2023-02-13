package com.pichincha.transacctions.service.impl;

import com.pichincha.transacctions.dto.AccountDto;
import com.pichincha.transacctions.enums.AccountTypeEnum;
import com.pichincha.transacctions.model.Account;
import com.pichincha.transacctions.model.Client;
import com.pichincha.transacctions.repository.AccountRepository;
import com.pichincha.transacctions.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void indexAccountTest() {
        List<Account> accounts = Arrays.asList(
                getAccount(),
                getAccount()
        );
        Mockito.when(accountRepository.findAll()).thenReturn(accounts);

        List<AccountDto> accountDto = accountService.indexAccount();

        assertNotNull(accountDto);
        assertEquals(2, accountDto.size());
    }

    private Account getAccount(){
        Account account = new Account();
         account.setId(UUID.randomUUID());
         account.setClient(new Client());
         account.setAccountNumber("4545454545");
         account.setType(AccountTypeEnum.CORRIENTE);
         account.setState(true);
         account.setInitialBalance(BigDecimal.TEN);
        return account;
    }
}