package com.npci.tests.performance;

import org.apache.http.entity.ContentType;

import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

public class UpipaymentTest {

    public static void main(String[] args) throws Exception {

        testPlan(
                threadGroup(1, 1,
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
