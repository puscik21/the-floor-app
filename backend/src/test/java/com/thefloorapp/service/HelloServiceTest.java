package com.thefloorapp.service;

import com.thefloorapp.entity.HelloEntry;
import com.thefloorapp.repository.HelloEntryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for HelloService.
 */
@ExtendWith(MockitoExtension.class)
class HelloServiceTest {

    @Mock
    private HelloEntryRepository helloEntryRepository;

    @InjectMocks
    private HelloService helloService;

    @BeforeEach
    void setUp() {
        // Setup is handled by MockitoExtension
    }

    @Test
    void testRecordHelloAndGetMessageFirstTime() {
        when(helloEntryRepository.save(any(HelloEntry.class))).thenReturn(new HelloEntry());
        when(helloEntryRepository.count()).thenReturn(1L);

        String result = helloService.recordHelloAndGetMessage();

        assertThat(result).isEqualTo("Hello! This endpoint has been called 1 time.");
        verify(helloEntryRepository).save(any(HelloEntry.class));
        verify(helloEntryRepository).count();
    }

    @Test
    void testRecordHelloAndGetMessageMultipleTimes() {
        when(helloEntryRepository.save(any(HelloEntry.class))).thenReturn(new HelloEntry());
        when(helloEntryRepository.count()).thenReturn(5L);

        String result = helloService.recordHelloAndGetMessage();

        assertThat(result).isEqualTo("Hello! This endpoint has been called 5 times.");
        verify(helloEntryRepository).save(any(HelloEntry.class));
        verify(helloEntryRepository).count();
    }

    @Test
    void testGetCount() {
        when(helloEntryRepository.count()).thenReturn(10L);

        long count = helloService.getCount();

        assertThat(count).isEqualTo(10L);
        verify(helloEntryRepository).count();
    }
}
