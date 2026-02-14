package com.thefloorapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Basic test to verify the application context loads successfully.
 */
@SpringBootTest
class TheFloorApplicationTests {

    @Test
    void contextLoads() {
        // This test verifies that the Spring application context loads successfully
        assertTrue(true, "Application context should load successfully");
    }
}

