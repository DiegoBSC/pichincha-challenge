package com.pichincha.transacctions.repository;

import com.pichincha.transacctions.enums.AccountTypeEnum;
import com.pichincha.transacctions.model.Account;
import com.pichincha.transacctions.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByTypeAndClient(AccountTypeEnum type, Client client);
    Optional<Account> findByAccountNumber(String number);
}
