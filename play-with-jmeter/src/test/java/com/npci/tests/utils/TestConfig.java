package com.npci.tests.utils;

public class TestConfig {

    // API Configuration
    public static final String BASE_URL =
            System.getProperty("api.base.url", "http://localhost:8080");

    public static final String BASE_PATH = "/api";

    public static final String FULL_URL = BASE_URL + BASE_PATH;

    // Load Test Configuration
    public static final int DEFAULT_THREADS =
            Integer.parseInt(System.getProperty("threads", "50"));

    public static final int DEFAULT_RAMPUP =
            Integer.parseInt(System.getProperty("rampup", "10"));

    public static final int DEFAULT_DURATION =
            Integer.parseInt(System.getProperty("duration", "60"));

    // Test Data
    public static final String TEST_DATA_PATH = "src/test/resources/test-data/";

    // Performance Thresholds
    public static final long MAX_RESPONSE_TIME_MS = 2000;
    public static final long MAX_95TH_PERCENTILE_MS = 1500;
    public static final double MAX_ERROR_RATE = 0.01; // 1%

    public static void printConfig() {
        System.out.println("\n=== Test Configuration ===");
        System.out.println("Base URL: " + BASE_URL);
        System.out.println("Base Path: " + BASE_PATH);
        System.out.println("Threads: " + DEFAULT_THREADS);
        System.out.println("Ramp-up: " + DEFAULT_RAMPUP + "s");
        System.out.println("Duration: " + DEFAULT_DURATION + "s");
        System.out.println("========================\n");
    }
}