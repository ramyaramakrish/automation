package com.npci.level6;

/**
 * Level 6: Composition - Contact Information
 *
 * ContactInfo is COMPOSED within Customer.
 * It's created when Customer is created and doesn't exist independently.
 */
public class ContactInfo {

    private String primaryPhone;
    private String secondaryPhone;
    private String email;
    private String alternateEmail;
    private boolean phoneVerified;
    private boolean emailVerified;

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTORS
    // ═══════════════════════════════════════════════════════════════

    public ContactInfo(String primaryPhone, String email) {
        this.primaryPhone = primaryPhone;
        this.email = email;
        this.phoneVerified = false;
        this.emailVerified = false;
    }

    public ContactInfo(String primaryPhone, String secondaryPhone, String email, String alternateEmail) {
        this.primaryPhone = primaryPhone;
        this.secondaryPhone = secondaryPhone;
        this.email = email;
        this.alternateEmail = alternateEmail;
        this.phoneVerified = false;
        this.emailVerified = false;
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS AND SETTERS
    // ═══════════════════════════════════════════════════════════════

    public String getPrimaryPhone() { return primaryPhone; }
    public void setPrimaryPhone(String phone) {
        this.primaryPhone = phone;
        this.phoneVerified = false;  // Need to re-verify
    }

    public String getSecondaryPhone() { return secondaryPhone; }
    public void setSecondaryPhone(String phone) { this.secondaryPhone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
        this.emailVerified = false;  // Need to re-verify
    }

    public String getAlternateEmail() { return alternateEmail; }
    public void setAlternateEmail(String email) { this.alternateEmail = email; }

    public boolean isPhoneVerified() { return phoneVerified; }
    public boolean isEmailVerified() { return emailVerified; }

    // ═══════════════════════════════════════════════════════════════
    // METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Validate phone number format
     */
    public boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    /**
     * Validate email format
     */
    public boolean isValidEmail(String email) {
        return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    /**
     * Verify phone (simulated OTP verification)
     */
    public boolean verifyPhone(String otp) {
        // Simulated verification
        if (otp != null && otp.length() == 6) {
            this.phoneVerified = true;
            System.out.println("Phone " + maskPhone(primaryPhone) + " verified successfully!");
            return true;
        }
        return false;
    }

    /**
     * Verify email (simulated link verification)
     */
    public boolean verifyEmail(String token) {
        // Simulated verification
        if (token != null && !token.isEmpty()) {
            this.emailVerified = true;
            System.out.println("Email " + maskEmail(email) + " verified successfully!");
            return true;
        }
        return false;
    }

    /**
     * Mask phone for display
     */
    public String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) return phone;
        return "XXXXXX" + phone.substring(phone.length() - 4);
    }

    /**
     * Mask email for display
     */
    public String maskEmail(String email) {
        if (email == null || !email.contains("@")) return email;
        int atIndex = email.indexOf("@");
        if (atIndex <= 2) return email;
        return email.substring(0, 2) + "****" + email.substring(atIndex);
    }

    /**
     * Display contact info
     */
    public void displayContactInfo() {
        System.out.println("  Primary Phone   : " + maskPhone(primaryPhone) +
                (phoneVerified ? " ✓" : " (Unverified)"));
        if (secondaryPhone != null && !secondaryPhone.isEmpty()) {
            System.out.println("  Secondary Phone : " + maskPhone(secondaryPhone));
        }
        System.out.println("  Email           : " + maskEmail(email) +
                (emailVerified ? " ✓" : " (Unverified)"));
        if (alternateEmail != null && !alternateEmail.isEmpty()) {
            System.out.println("  Alternate Email : " + maskEmail(alternateEmail));
        }
    }

    @Override
    public String toString() {
        return "Phone: " + maskPhone(primaryPhone) + " | Email: " + maskEmail(email);
    }
}