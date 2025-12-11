package com.npci.gateway.service;

import com.npci.gateway.dto.TransactionResponse;
import com.npci.gateway.dto.UpiPaymentRequest;
import com.npci.gateway.exception.InvalidUpiIdException;
import com.npci.gateway.model.Transaction;
import com.npci.gateway.model.UpiId;
import com.npci.gateway.repository.TransactionRepository;
import com.npci.gateway.repository.UpiIdRepository;
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
public class UpiService {
    
    private final UpiIdRepository upiIdRepository;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionIdGenerator transactionIdGenerator;
    
    @Value("${app.simulation.delay.min:100}")
    private int minDelay;
    
    @Value("${app.simulation.delay.max:500}")
    private int maxDelay;
    
    private final Random random = new Random();
    
    public boolean validateUpiId(String upiId) {
        simulateProcessingDelay();
        return upiIdRepository.existsByUpiId(upiId);
    }
    
    @Transactional
    public TransactionResponse initiatePayment(UpiPaymentRequest request) {
        simulateProcessingDelay();
        
        UpiId fromUpi = upiIdRepository.findByUpiId(request.getFromUpiId())
                .orElseThrow(() -> new InvalidUpiIdException(
                        "Invalid sender UPI ID: " + request.getFromUpiId()));
        
        UpiId toUpi = upiIdRepository.findByUpiId(request.getToUpiId())
                .orElseThrow(() -> new InvalidUpiIdException(
                        "Invalid receiver UPI ID: " + request.getToUpiId()));
        
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionIdGenerator.generateUpiTransactionId());
        transaction.setTransactionType("UPI");
        transaction.setFromAccount(fromUpi.getAccount());
        transaction.setToAccount(toUpi.getAccount());
        transaction.setAmount(request.getAmount());
        transaction.setStatus("PROCESSING");
        transaction.setRemarks(request.getRemarks());
        
        transactionRepository.save(transaction);
        
        try {
            accountService.debitAccount(
                    fromUpi.getAccount().getAccountNumber(), 
                    request.getAmount()
            );
            
            accountService.creditAccount(
                    toUpi.getAccount().getAccountNumber(), 
                    request.getAmount()
            );
            
            transaction.setStatus("SUCCESS");
            transactionRepository.save(transaction);
            
            log.info("UPI payment successful: {}", transaction.getTransactionId());
            
        } catch (Exception e) {
            transaction.setStatus("FAILED");
            transaction.setFailureReason(e.getMessage());
            transactionRepository.save(transaction);
            
            log.error("UPI payment failed: {}", e.getMessage());
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
