package com.thefloorapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.cors")
public record AppCorsProperties(String[] allowedOrigins) {
    public AppCorsProperties {
        if (allowedOrigins == null) {
            allowedOrigins = new String[0];
        }
    }
}