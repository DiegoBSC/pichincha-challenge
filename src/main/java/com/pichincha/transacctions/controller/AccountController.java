package com.pichincha.transacctions.controller;

import com.pichincha.transacctions.dto.AccountDto;
import com.pichincha.transacctions.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/index")
    public List<AccountDto> index(){
        return accountService.indexAccount();
    }

    @GetMapping("/show/{accountId}")
    public AccountDto showAccount(@PathVariable UUID accountId){
        return accountService.findAccountById(accountId);
    }

    @PostMapping("/create")
    public AccountDto createClient(@Valid @RequestBody AccountDto accountDto){
        return accountService.createAccount(accountDto);
    }

    @PutMapping("/update/{accountId}")
    public AccountDto updateClient(@Valid @PathVariable UUID accountId,  @RequestBody AccountDto accountDto){
        return accountService.updateAccount(accountId, accountDto);
    }

    @DeleteMapping("/delete/{accountId}")
    public void deleteClient(@PathVariable UUID accountId){
        accountService.deleteAccount(accountId);
    }

}
