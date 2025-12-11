package com.npci.gateway.controller;

import com.npci.gateway.dto.ApiResponse;
import com.npci.gateway.dto.ImpsTransferRequest;
import com.npci.gateway.dto.TransactionResponse;
import com.npci.gateway.service.ImpsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/imps")
@RequiredArgsConstructor
public class ImpsController {
    
    private final ImpsService impsService;
    
    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<TransactionResponse>> initiateTransfer(
            @Valid @RequestBody ImpsTransferRequest request) {
        TransactionResponse response = impsService.initiateTransfer(request);
        
        if ("SUCCESS".equals(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Transfer initiated successfully", response));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Transfer failed: " + response.getFailureReason()));
        }
    }
}
