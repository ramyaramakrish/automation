package com.npci.level5;

import java.time.LocalDateTime;

/**
 * Level 5: Abstraction - Interface for Audit Trail
 *
 * This interface defines audit logging capabilities.
 * Any class that needs audit logging implements this interface.
 * Demonstrates: Interface for cross-cutting concerns
 */
public interface Auditable {

    // Constants
    String AUDIT_VERSION = "1.0";

    // Abstract methods
    /**
     * Get audit log for this entity
     */
    String getAuditLog();

    /**
     * Record an audit event
     */
    void recordAuditEvent(String eventType, String details);

    /**
     * Get entity ID for audit purposes
     */
    String getAuditEntityId();

    /**
     * Get entity type for audit purposes
     */
    String getAuditEntityType();

    // Default methods
    /**
     * Format audit entry
     */
    default String formatAuditEntry(String eventType, String details) {
        return String.format("[%s] %s | %s | %s | %s",
                LocalDateTime.now(),
                getAuditEntityType(),
                getAuditEntityId(),
                eventType,
                details);
    }

    /**
     * Check if audit is enabled
     */
    default boolean isAuditEnabled() {
        return true;
    }
}