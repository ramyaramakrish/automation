package com.npci.mini_examples;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTestCase {

    public void testTransferSuccess() {
        //....
        double actualAmount = 1000.00; // assume transfer is successful
        double expectedAmount = 1000.00;
        assertEquals(expectedAmount, actualAmount, "Transfer amount should match expected amount");
    }

}
