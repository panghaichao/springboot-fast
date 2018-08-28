package com.cctc.fast.core.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
 * 
 * WebSocketConfig
 * webSocket配置类，绑定前端连接端点url及其他信息
 * @author Hejeo
 *
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new WebSocketMessageHandler(), "/websocketApp").addInterceptors(new WebSocketInterceptor()).setAllowedOrigins("*");
	}
}
