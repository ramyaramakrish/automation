package com.npci.gateway.config;

import com.npci.gateway.model.Account;
import com.npci.gateway.model.UpiId;
import com.npci.gateway.repository.AccountRepository;
import com.npci.gateway.repository.UpiIdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    
    private final AccountRepository accountRepository;
    private final UpiIdRepository upiIdRepository;
    
    @Override
    public void run(String... args) {
        if (accountRepository.count() == 0) {
            loadSampleData();
        }
    }
    
    private void loadSampleData() {
        log.info("Loading sample data...");
        
        // Create accounts
        for (int i = 1; i <= 100; i++) {
            Account account = new Account();
            account.setAccountNumber(String.format("ACC%06d", i));
            account.setAccountHolderName("Test User " + i);
            account.setBalance(new BigDecimal(100000));
            account.setIfscCode("SBIN0001234");
            account.setBankName("State Bank of India");
            account.setAccountType("SAVINGS");
            account.setStatus("ACTIVE");
            account.setCreatedAt(LocalDateTime.now());
            account.setUpdatedAt(LocalDateTime.now());
            
            Account savedAccount = accountRepository.save(account);
            
            // Create UPI ID for each account
            UpiId upiId = new UpiId();
            upiId.setUpiId(String.format("user%d@upi", i));
            upiId.setAccount(savedAccount);
            upiId.setIsPrimary(true);
            upiId.setStatus("ACTIVE");
            upiId.setCreatedAt(LocalDateTime.now());
            
            upiIdRepository.save(upiId);
        }
        
        log.info("Sample data loaded successfully: 100 accounts with UPI IDs");
    }
}
