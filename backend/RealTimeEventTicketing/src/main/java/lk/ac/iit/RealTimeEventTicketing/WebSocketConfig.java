package lk.ac.iit.RealTimeEventTicketing;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register WebSocket endpoint with CORS support
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:4200") // CORS for WebSocket handshake
                .withSockJS(); // Allow fallback options (e.g., if WebSocket is not available)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");  // Enable a simple in-memory broker
        registry.setApplicationDestinationPrefixes("/app");  // Prefix for client messages
    }
}
