package com.npci.gateway.repository;

import com.npci.gateway.model.UpiId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UpiIdRepository extends JpaRepository<UpiId, Long> {
    Optional<UpiId> findByUpiId(String upiId);
    boolean existsByUpiId(String upiId);
}
