package com.npci.gateway.service;

import com.npci.gateway.dto.TransactionResponse;
import com.npci.gateway.exception.TransactionNotFoundException;
import com.npci.gateway.model.Transaction;
import com.npci.gateway.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    
    public TransactionResponse getTransactionStatus(String transactionId) {
        Transaction transaction = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(
                        "Transaction not found: " + transactionId));
        
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
}
