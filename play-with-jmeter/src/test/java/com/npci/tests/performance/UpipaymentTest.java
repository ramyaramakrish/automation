package com.npci.tests.performance;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;

import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

public class UpipaymentTest {

    @Test
    public void testUpiPaymentPerformance() throws Exception {
        testPlan(
                // Generate HTML report in target/jmeter-report
                htmlReporter("target/jmeter-report"),
                threadGroup(100, 1,
                        httpSampler("http://localhost:8080/api/upi/payment")
                                .post("{\n" +
                                        "  \"fromUpiId\": \"user1@upi\",\n" +
                                        "  \"toUpiId\": \"user99@upi\",\n" +
                                        "  \"amount\": 1000,\n" +
                                        "  \"remarks\": \"Test\"\n" +
                                        "}", ContentType.APPLICATION_JSON)
                )
        ).run();
    }
}
