package com.npci.gateway.controller;

import com.npci.gateway.dto.ApiResponse;
import com.npci.gateway.model.Account;
import com.npci.gateway.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    
    private final AccountService accountService;
    
    @GetMapping("/{accountNumber}")
    public ResponseEntity<ApiResponse<Account>> getAccount(@PathVariable String accountNumber) {
        Account account = accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok(ApiResponse.success("Account retrieved successfully", account));
    }
    
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<ApiResponse<String>> getBalance(@PathVariable String accountNumber) {
        Account account = accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok(
            ApiResponse.success("Balance retrieved", "Balance: " + account.getBalance())
        );
    }
}
