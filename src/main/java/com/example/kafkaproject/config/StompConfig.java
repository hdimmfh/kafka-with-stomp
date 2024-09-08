package com.example.kafkaproject.config;

import com.example.kafkaproject.handler.StompInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class StompConfig implements WebSocketMessageBrokerConfigurer {
    private final StompInterceptor stompInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // STOMP Socket Endpoint
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 구독자의 메세지 조회 경로 (메세지 이동 : 브로커 -> 구독자)
        registry.enableSimpleBroker("/topic");
        // 구독자의 메세지 저장 경로 (메세지 이동 : 구독자 -> 브로커)
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // STOMP event driven Interceptor
        registration.interceptors(stompInterceptor);
    }
}