package com.npci.gateway.controller;

import com.npci.gateway.dto.ApiResponse;
import com.npci.gateway.dto.TransactionResponse;
import com.npci.gateway.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    
    private final TransactionService transactionService;
    
    @GetMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<TransactionResponse>> getTransactionStatus(
            @PathVariable String transactionId) {
        TransactionResponse response = transactionService.getTransactionStatus(transactionId);
        return ResponseEntity.ok(
            ApiResponse.success("Transaction retrieved successfully", response)
        );
    }
}
