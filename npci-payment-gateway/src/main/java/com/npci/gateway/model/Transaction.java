package com.npci.gateway.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String transactionId;
    
    @Column(nullable = false)
    private String transactionType;
    
    @ManyToOne
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;
    
    @ManyToOne
    @JoinColumn(name = "to_account_id", nullable = false)
    private Account toAccount;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private String status;
    
    private String remarks;
    
    private String failureReason;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
