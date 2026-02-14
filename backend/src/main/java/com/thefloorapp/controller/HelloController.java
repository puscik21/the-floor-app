package com.thefloorapp.controller;

import com.thefloorapp.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for hello endpoints.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HelloController {

    private final HelloService helloService;

    /**
     * Returns a hello message and records the call in the database.
     *
     * @return hello message with call count
     */
    @GetMapping("/hello")
    public String sayHello() {
        return helloService.recordHelloAndGetMessage();
    }
}

