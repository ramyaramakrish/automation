package com.npci.gateway.util;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class TransactionIdGenerator {
    
    public String generateUpiTransactionId() {
        return "UPI" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    public String generateImpsTransactionId() {
        return "IMPS" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    public String generateNeftTransactionId() {
        return "NEFT" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
