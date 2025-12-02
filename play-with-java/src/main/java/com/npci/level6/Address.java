package com.npci.level6;

/**
 * Level 6: Composition & Aggregation
 *
 * Key Concepts:
 * - Composition = Strong "HAS-A" relationship (owns)
 *   - Child object cannot exist without parent
 *   - Parent creates and destroys child
 *   - Example: BankAccount HAS-A Address (address is part of account)
 *
 * - Aggregation = Weak "HAS-A" relationship (uses)
 *   - Child object can exist independently
 *   - Parent uses but doesn't own child
 *   - Example: Customer HAS-A BankAccount (account can exist without customer reference)
 *
 * This Address class will be used in COMPOSITION relationships.
 * An Address is PART OF a Customer/Branch - created with them, dies with them.
 */
public class Address {

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String pincode;
    private String country;
    private String addressType;  // PERMANENT, CORRESPONDENCE, OFFICE

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTORS
    // ═══════════════════════════════════════════════════════════════

    public Address(String addressLine1, String city, String state, String pincode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = "";
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.country = "India";
        this.addressType = "PERMANENT";
    }

    public Address(String addressLine1, String addressLine2, String city,
                   String state, String pincode, String addressType) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.country = "India";
        this.addressType = addressType;
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS AND SETTERS
    // ═══════════════════════════════════════════════════════════════

    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }

    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getCountry() { return country; }
    public String getAddressType() { return addressType; }

    // ═══════════════════════════════════════════════════════════════
    // METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Get full address as formatted string
     */
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(addressLine1);
        if (addressLine2 != null && !addressLine2.isEmpty()) {
            sb.append(", ").append(addressLine2);
        }
        sb.append(", ").append(city);
        sb.append(", ").append(state);
        sb.append(" - ").append(pincode);
        sb.append(", ").append(country);
        return sb.toString();
    }

    /**
     * Get short address
     */
    public String getShortAddress() {
        return city + ", " + state + " - " + pincode;
    }

    /**
     * Validate pincode
     */
    public boolean isValidPincode() {
        return pincode != null && pincode.matches("\\d{6}");
    }

    @Override
    public String toString() {
        return "[" + addressType + "] " + getFullAddress();
    }

    /**
     * Display formatted address
     */
    public void displayAddress() {
        System.out.println("  Address Type : " + addressType);
        System.out.println("  Line 1       : " + addressLine1);
        if (addressLine2 != null && !addressLine2.isEmpty()) {
            System.out.println("  Line 2       : " + addressLine2);
        }
        System.out.println("  City         : " + city);
        System.out.println("  State        : " + state);
        System.out.println("  Pincode      : " + pincode);
        System.out.println("  Country      : " + country);
    }
}