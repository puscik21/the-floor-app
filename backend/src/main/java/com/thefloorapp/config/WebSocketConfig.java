package com.thefloorapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configures the STOMP WebSocket message broker.
 *
 * <p>Clients connect at {@code /ws}, send messages to {@code /app/**},
 * and subscribe to broadcasts on {@code /topic/**}.</p>
 */
@Configuration
@EnableWebSocketMessageBroker
@EnableConfigurationProperties(AppCorsProperties.class)
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final AppCorsProperties appCorsProperties;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Simple in-memory broker for broadcasting to subscribers
        registry.enableSimpleBroker("/topic");

        // Prefix for messages routed to @MessageMapping methods
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        StompWebSocketEndpointRegistration registration = registry.addEndpoint("/ws");
        if (appCorsProperties.allowedOrigins().length > 0) {
            registration.setAllowedOrigins(appCorsProperties.allowedOrigins());
        }
        registration.withSockJS();
    }
}

