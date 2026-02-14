package com.thefloorapp.service;

import com.thefloorapp.entity.HelloEntry;
import com.thefloorapp.repository.HelloEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service for managing hello greetings.
 */
@Service
@RequiredArgsConstructor
public class HelloService {

    private final HelloEntryRepository helloEntryRepository;

    /**
     * Records a new hello entry and returns the total count.
     *
     * @return greeting message with total count
     */
    @Transactional
    public String recordHelloAndGetMessage() {
        HelloEntry entry = HelloEntry.builder()
                .timestamp(LocalDateTime.now())
                .build();
        helloEntryRepository.save(entry);

        long count = helloEntryRepository.count();
        return String.format("Hello! This endpoint has been called %d time%s.",
                count, count == 1 ? "" : "s");
    }

    /**
     * Gets the total count of hello entries.
     *
     * @return total count
     */
    public long getCount() {
        return helloEntryRepository.count();
    }
}
