package com.pichincha.transacctions.service.impl;

import com.pichincha.transacctions.dto.AccountDto;
import com.pichincha.transacctions.dto.ClientDto;
import com.pichincha.transacctions.enums.AccountTypeEnum;
import com.pichincha.transacctions.enums.GenderEnum;
import com.pichincha.transacctions.model.Account;
import com.pichincha.transacctions.model.Client;
import com.pichincha.transacctions.repository.AccountRepository;
import com.pichincha.transacctions.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    @Test
    void createAccountTest() {
        Client client = getClient();
        ClientDto clientDto = getClientDto();
        AccountDto accountDto = getAccountDto();
        accountDto.setClient(clientDto);
        Account account = getAccount();
        account.setClient(client);

        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.of(client));
        Mockito.when(accountRepository.findByTypeAndClient(account.getType(), client)).thenReturn(Optional.empty());
        Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        Mockito.when(modelMapper.map(Mockito.any(AccountDto.class), Mockito.any())).thenReturn(account);
        Mockito.when(modelMapper.map(Mockito.any(Account.class), Mockito.any())).thenReturn(accountDto);

        AccountDto savedAccountDto = accountService.createAccount(accountDto);

        assertNotNull(savedAccountDto);
        assertEquals(accountDto.getAccountNumber(), savedAccountDto.getAccountNumber());
        assertEquals(accountDto.getType(), savedAccountDto.getType());
    }

    @Test
    void testCreateAccountClientNotFound() {
        AccountDto accountDto = getAccountDto();
        ClientDto clientDto = getClientDto();
        accountDto.setClient(clientDto);

        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            accountService.createAccount(accountDto);
        }, "Cliente no existe, la cuenta no puede ser creeada, debe crear el cliente");
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

    private Client getClient(){
        Client client = new Client();
        client.setId(UUID.randomUUID());
        client.setName("Esteban Perez");
        client.setGender(GenderEnum.MASCULINO);
        client.setAge(35);
        client.setAddress("Calle A y Calle B");
        client.setTelephone("09999999");
        client.setPassword("123455777");
        return client;
    }

    private ClientDto getClientDto(){
        ClientDto clientDto = new ClientDto();
        clientDto.setId(UUID.randomUUID());
        clientDto.setName("Esteban Perez");
        clientDto.setGender(GenderEnum.MASCULINO);
        clientDto.setAge(35);
        clientDto.setAddress("Calle A y Calle B");
        clientDto.setTelephone("09999999");
        clientDto.setPassword("123455777");
        return clientDto;
    }

    private AccountDto getAccountDto(){
        AccountDto accountDto = new AccountDto();
        accountDto.setId(UUID.randomUUID());
        accountDto.setAccountNumber("55599988800");
        accountDto.setClient(getClientDto());
        accountDto.setState(true);
        accountDto.setType(AccountTypeEnum.CORRIENTE);
        accountDto.setInitialBalance(BigDecimal.TEN);
        return accountDto;
    }

}