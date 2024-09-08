package com.example.kafkaproject.handler;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@RequiredArgsConstructor
@Component
public class StompInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(@NonNull Message<?> message,@NonNull MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        // 필요시 기타 로직 구현

        // 유저 인증이 필요한 경우 헤더에 접근
        // String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader("Authorization"));
        return message;
    }

    @EventListener
    public void handleWebSocketConnectionListener(SessionConnectedEvent event){
        System.out.println("사용자 입장");
        // 필요시 기타 로직 구현
    }

    @EventListener
    public void handleWebSocketDisconnectionListener(SessionDisconnectEvent event){
        System.out.println("사용자 퇴장");
        // 필요시 기타 로직 구현
    }
}