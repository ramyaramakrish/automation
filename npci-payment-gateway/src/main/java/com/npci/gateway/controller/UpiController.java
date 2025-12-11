package com.npci.gateway.controller;

import com.npci.gateway.dto.ApiResponse;
import com.npci.gateway.dto.TransactionResponse;
import com.npci.gateway.dto.UpiPaymentRequest;
import com.npci.gateway.service.UpiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/upi")
@RequiredArgsConstructor
public class UpiController {
    
    private final UpiService upiService;
    
    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<Boolean>> validateUpiId(@RequestBody String upiId) {
        boolean isValid = upiService.validateUpiId(upiId);
        if (isValid) {
            return ResponseEntity.ok(ApiResponse.success("UPI ID is valid", true));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("UPI ID not found"));
    }
    
    @PostMapping("/payment")
    public ResponseEntity<ApiResponse<TransactionResponse>> initiatePayment(
            @Valid @RequestBody UpiPaymentRequest request) {
        TransactionResponse response = upiService.initiatePayment(request);
        
        if ("SUCCESS".equals(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Payment initiated successfully", response));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Payment failed: " + response.getFailureReason()));
        }
    }
}
