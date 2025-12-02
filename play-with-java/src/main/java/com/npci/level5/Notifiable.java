package com.npci.level5;

/**
 * Level 5: Abstraction - Interface for Notifications
 *
 * This interface defines notification capabilities.
 * Any class that needs to send notifications implements this interface.
 * Demonstrates: Interface for cross-cutting concerns
 */
public interface Notifiable {

    // Notification channels
    String CHANNEL_SMS = "SMS";
    String CHANNEL_EMAIL = "EMAIL";
    String CHANNEL_PUSH = "PUSH";
    String CHANNEL_ALL = "ALL";

    // Abstract methods
    /**
     * Send notification
     */
    void sendNotification(String recipient, String message, String channel);

    /**
     * Get notification template
     */
    String getNotificationTemplate(String templateType);

    // Default methods
    /**
     * Send SMS notification
     */
    default void sendSMS(String phoneNumber, String message) {
        System.out.println("[SMS] To: " + phoneNumber);
        System.out.println("      Message: " + message);
    }

    /**
     * Send Email notification
     */
    default void sendEmail(String email, String subject, String body) {
        System.out.println("[EMAIL] To: " + email);
        System.out.println("        Subject: " + subject);
        System.out.println("        Body: " + body);
    }

    /**
     * Send Push notification
     */
    default void sendPushNotification(String deviceId, String title, String message) {
        System.out.println("[PUSH] Device: " + deviceId);
        System.out.println("       Title: " + title);
        System.out.println("       Message: " + message);
    }

    /**
     * Format currency for notification
     */
    default String formatAmountForNotification(double amount) {
        if (amount >= 10000000) {
            return String.format("Rs.%.2f Cr", amount / 10000000);
        } else if (amount >= 100000) {
            return String.format("Rs.%.2f L", amount / 100000);
        } else {
            return String.format("Rs.%.2f", amount);
        }
    }
}