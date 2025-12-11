package com.npci.gateway.service;

import com.npci.gateway.dto.ImpsTransferRequest;
import com.npci.gateway.dto.TransactionResponse;
import com.npci.gateway.model.Account;
import com.npci.gateway.model.Transaction;
import com.npci.gateway.repository.TransactionRepository;
import com.npci.gateway.util.TransactionIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImpsService {
    
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionIdGenerator transactionIdGenerator;
    
    @Value("${app.simulation.delay.min:100}")
    private int minDelay;
    
    @Value("${app.simulation.delay.max:500}")
    private int maxDelay;
    
    private final Random random = new Random();
    
    @Transactional
    public TransactionResponse initiateTransfer(ImpsTransferRequest request) {
        simulateProcessingDelay();
        
        Account fromAccount = accountService.getAccountByNumber(request.getFromAccount());
        Account toAccount = accountService.getAccountByNumber(request.getToAccount());
        
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionIdGenerator.generateImpsTransactionId());
        transaction.setTransactionType("IMPS");
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(request.getAmount());
        transaction.setStatus("PROCESSING");
        transaction.setRemarks(request.getRemarks());
        
        transactionRepository.save(transaction);
        
        try {
            accountService.debitAccount(request.getFromAccount(), request.getAmount());
            accountService.creditAccount(request.getToAccount(), request.getAmount());
            
            transaction.setStatus("SUCCESS");
            transactionRepository.save(transaction);
            
            log.info("IMPS transfer successful: {}", transaction.getTransactionId());
            
        } catch (Exception e) {
            transaction.setStatus("FAILED");
            transaction.setFailureReason(e.getMessage());
            transactionRepository.save(transaction);
            
            log.error("IMPS transfer failed: {}", e.getMessage());
        }
        
        return buildTransactionResponse(transaction);
    }
    
    private TransactionResponse buildTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .fromAccount(transaction.getFromAccount().getAccountNumber())
                .toAccount(transaction.getToAccount().getAccountNumber())
                .remarks(transaction.getRemarks())
                .failureReason(transaction.getFailureReason())
                .timestamp(transaction.getCreatedAt())
                .build();
    }
    
    private void simulateProcessingDelay() {
        try {
            int delay = minDelay + random.nextInt(maxDelay - minDelay);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
