package com.pichincha.transacctions.service.impl;

import com.pichincha.transacctions.dto.AccountDto;
import com.pichincha.transacctions.model.Account;
import com.pichincha.transacctions.model.Client;
import com.pichincha.transacctions.repository.AccountRepository;
import com.pichincha.transacctions.repository.ClientRepository;
import com.pichincha.transacctions.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final ModelMapper modelMapper;

    private final ClientRepository clientRepository;

    @Override
    public List<AccountDto> indexAccount() {
        return accountRepository.findAll().stream().map(account -> modelMapper.map(account, AccountDto.class)
        ).toList();
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Client client = clientRepository.findById(accountDto.getClient().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Cliente no existe, la cuenta no puede ser creeada, debe crear el cliente"));

       Optional<Account> accountTypeAndClient =  accountRepository.findByTypeAndClient(accountDto.getType(), client);
        if(accountTypeAndClient.isPresent())
            throw  new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Cliente ya tiene una cuenta del mismo tipo");

        Optional<Account> accountNumber = accountRepository.findByAccountNumber(accountDto.getAccountNumber());
        if(accountNumber.isPresent())
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "El numero de cuenta ya se encuentra registrado");

       Account account = modelMapper.map(accountDto, Account.class);
       account.setClient(client);
        return modelMapper.map(accountRepository.save(account), AccountDto.class);
    }

    @Override
    public AccountDto updateAccount(UUID accountId, AccountDto accountDto) {
        Account account = getAccountById(accountId);

        BigDecimal initialAmount = account.getInitialBalance().setScale(2, RoundingMode.HALF_UP);
        BigDecimal modifyInitialAmount = accountDto.getInitialBalance().setScale(2, RoundingMode.HALF_UP);

        if ((!initialAmount.equals(modifyInitialAmount)) && !account.getMovements().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "Error: La cuenta ya realizo movimientos es imposible actualizar el saldo inicial");
        }

        account.setAccountNumber(accountDto.getAccountNumber());
        account.setState(accountDto.getState());
        account.setType(accountDto.getType());
        account.setInitialBalance(accountDto.getInitialBalance());

        return modelMapper.map(accountRepository.save(account), AccountDto.class);
    }

    @Override
    public void deleteAccount(UUID accountId) {
        Account account = getAccountById(accountId);

        if(!account.getMovements().isEmpty())
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "Error: La cuenta ya realizo movimientos es imposible eliminar la cuenta");

        accountRepository.delete(account);
    }

    @Override
    public AccountDto findAccountById(UUID accountId) {
        return modelMapper.map(getAccountById(accountId), AccountDto.class);
    }

    private Account getAccountById(UUID accountId){
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                        "Error: No se encuenta la cuenta del cliente no es posible realizar la accion"));
    }
}
