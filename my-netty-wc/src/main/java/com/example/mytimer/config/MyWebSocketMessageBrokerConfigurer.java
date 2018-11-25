package com.example.mytimer.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//@Configuration
//@EnableWebSocketMessageBroker
public class MyWebSocketMessageBrokerConfigurer implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // 客户端的连接地址:
        // socket = new WebSocket("ws://localhost:8080/websocket-simple");
        registry.addEndpoint("/websocket-simple")
                .setAllowedOrigins("*")
                .withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic","/queue");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {

        WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);
    }

}
