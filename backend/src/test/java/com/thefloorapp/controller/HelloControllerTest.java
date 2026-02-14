package com.thefloorapp.controller;

import com.thefloorapp.service.HelloService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for HelloController.
 */
@ExtendWith(MockitoExtension.class)
class HelloControllerTest {

    @Mock
    private HelloService helloService;

    @InjectMocks
    private HelloController helloController;

    @Test
    void testSayHello() {
        String expectedMessage = "Hello! This endpoint has been called 1 time.";
        when(helloService.recordHelloAndGetMessage()).thenReturn(expectedMessage);

        String result = helloController.sayHello();

        assert result.equals(expectedMessage);
        verify(helloService).recordHelloAndGetMessage();
    }
}

