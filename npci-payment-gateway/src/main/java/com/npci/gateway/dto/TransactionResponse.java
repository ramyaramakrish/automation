package com.npci.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private String transactionId;
    private String transactionType;
    private BigDecimal amount;
    private String status;
    private String fromAccount;
    private String toAccount;
    private String remarks;
    private String failureReason;
    private LocalDateTime timestamp;
}
