package com.npci.gateway.service;

import com.npci.gateway.exception.AccountNotFoundException;
import com.npci.gateway.exception.InsufficientBalanceException;
import com.npci.gateway.model.Account;
import com.npci.gateway.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    
    private final AccountRepository accountRepository;
    
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(
                        "Account not found: " + accountNumber));
    }
    
    @Transactional
    public void debitAccount(String accountNumber, BigDecimal amount) {
        Account account = getAccountByNumber(accountNumber);
        
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(
                    "Insufficient balance in account: " + accountNumber);
        }
        
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        
        log.info("Debited {} from account {}", amount, accountNumber);
    }
    
    @Transactional
    public void creditAccount(String accountNumber, BigDecimal amount) {
        Account account = getAccountByNumber(accountNumber);
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        
        log.info("Credited {} to account {}", amount, accountNumber);
    }
}
