package com.npci.gateway.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpiPaymentRequest {
    @NotBlank(message = "From UPI ID is required")
    @Pattern(regexp = "^[a-zA-Z0-9.\\-_]{2,}@[a-zA-Z]{2,}$", message = "Invalid UPI ID format")
    private String fromUpiId;
    
    @NotBlank(message = "To UPI ID is required")
    @Pattern(regexp = "^[a-zA-Z0-9.\\-_]{2,}@[a-zA-Z]{2,}$", message = "Invalid UPI ID format")
    private String toUpiId;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1.0", message = "Amount must be at least 1")
    @DecimalMax(value = "100000.0", message = "Amount cannot exceed 100000")
    private BigDecimal amount;
    
    private String remarks;
}
