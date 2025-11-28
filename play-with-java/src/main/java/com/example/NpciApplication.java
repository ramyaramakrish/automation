package com.example;

public class NpciApplication {

    public static void main(String[] args) {

        PaymentService upiPaymentService = new UPIPayment("s1@oksbi", "r1@okicic", 100.00, "1234");
        upiPaymentService.executePayment();
        PaymentService impsPaymentService = new IMPSPayment("9876543210", "0123456789", "CAN00000163", 100.00);
        impsPaymentService.executePayment();

    }

}
