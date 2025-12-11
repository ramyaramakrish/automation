package com.npci.gateway.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ImpsTransferRequest {
    @NotBlank(message = "From account number is required")
    private String fromAccount;
    
    @NotBlank(message = "To account number is required")
    private String toAccount;
    
    @NotBlank(message = "IFSC code is required")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format")
    private String ifscCode;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1.0", message = "Amount must be at least 1")
    @DecimalMax(value = "200000.0", message = "Amount cannot exceed 200000")
    private BigDecimal amount;
    
    private String remarks;
}
