package com.npci.gateway.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "upi_ids")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpiId {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String upiId;
    
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    
    @Column(nullable = false)
    private Boolean isPrimary;
    
    @Column(nullable = false)
    private String status;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
