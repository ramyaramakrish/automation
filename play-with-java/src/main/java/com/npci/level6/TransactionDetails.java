package com.npci.level6;

import java.time.LocalDateTime;

/**
 * Level 6: Composition - Transaction Details
 *
 * TransactionDetails is COMPOSED within Transaction.
 * It contains additional details about a transaction and cannot exist independently.
 */
public class TransactionDetails {

    private String channel;           // UPI, IMPS, NEFT, ATM, BRANCH
    private String deviceId;
    private String ipAddress;
    private String location;
    private String remarks;
    private LocalDateTime processedAt;
    private String referenceNumber;

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════════

    public TransactionDetails(String channel, String remarks) {
        this.channel = channel;
        this.remarks = remarks;
        this.processedAt = LocalDateTime.now();
        this.referenceNumber = generateReference();
    }

    public TransactionDetails(String channel, String deviceId, String ipAddress,
                              String location, String remarks) {
        this.channel = channel;
        this.deviceId = deviceId;
        this.ipAddress = ipAddress;
        this.location = location;
        this.remarks = remarks;
        this.processedAt = LocalDateTime.now();
        this.referenceNumber = generateReference();
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public String getChannel() { return channel; }
    public String getDeviceId() { return deviceId; }
    public String getIpAddress() { return ipAddress; }
    public String getLocation() { return location; }
    public String getRemarks() { return remarks; }
    public LocalDateTime getProcessedAt() { return processedAt; }
    public String getReferenceNumber() { return referenceNumber; }

    // ═══════════════════════════════════════════════════════════════
    // METHODS
    // ═══════════════════════════════════════════════════════════════

    private String generateReference() {
        return "REF" + System.currentTimeMillis();
    }

    public void displayDetails() {
        System.out.println("    Channel    : " + channel);
        System.out.println("    Reference  : " + referenceNumber);
        System.out.println("    Processed  : " + processedAt);
        if (location != null) {
            System.out.println("    Location   : " + location);
        }
        if (remarks != null && !remarks.isEmpty()) {
            System.out.println("    Remarks    : " + remarks);
        }
    }

    @Override
    public String toString() {
        return "[" + channel + "] " + referenceNumber + " | " + remarks;
    }
}