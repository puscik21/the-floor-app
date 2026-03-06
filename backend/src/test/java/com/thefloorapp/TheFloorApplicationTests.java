package com.thefloorapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TheFloorApplicationTests {

    @Test
    void contextLoads() {
        assertThat(true).as("Application context should load successfully").isTrue();
    }
}
