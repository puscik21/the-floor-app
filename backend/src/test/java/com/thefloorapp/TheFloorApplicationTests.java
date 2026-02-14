package com.thefloorapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic test to verify the application context loads successfully.
 */
@SpringBootTest
class TheFloorApplicationTests {

    @Test
    void contextLoads() {
        // This test verifies that the Spring application context loads successfully
        assertThat(true).as("Application context should load successfully").isTrue();
    }
}
