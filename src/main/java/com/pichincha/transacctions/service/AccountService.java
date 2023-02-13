package com.pichincha.transacctions.service;

import com.pichincha.transacctions.dto.AccountDto;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    List<AccountDto> indexAccount();
    AccountDto createAccount(AccountDto accountDto);
    AccountDto updateAccount(UUID accountId, AccountDto accountDto);
    void deleteAccount(UUID accountId);
    AccountDto findAccountById(UUID accountId);
}
