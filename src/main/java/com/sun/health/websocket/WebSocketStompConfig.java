package com.sun.health.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Created by 华硕 on 2018-06-12.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer {
    /**
     * 注册stomp的端点
     * @param stompEndpointRegistry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // 运行使用socketJS方式 访问点是server 允许跨域
        // 网页中使用 http://host:port/server 连接WebSocket服务器
        stompEndpointRegistry.addEndpoint("server").setAllowedOrigins("*");//.withSockJS();
    }

    /**
     * 配置信息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 订阅Broker名称
        registry.enableSimpleBroker("queue", "topic");
        // 全局使用的消息前缀
        registry.setApplicationDestinationPrefixes("app");
        // 点对点使用的订阅前缀(客户端订阅路径上会体现),默认为/user/
//        registry.setUserDestinationPrefix("/user");
    }

}
