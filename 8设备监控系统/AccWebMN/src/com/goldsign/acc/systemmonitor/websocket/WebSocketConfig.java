package com.goldsign.acc.systemmonitor.websocket;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Description:
 *
 * @author: zhongziqi
 * @Date: 2018-07-04
 * @Time: 11:45
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private Logger logger = Logger.getLogger(WebSocketConfig.class);
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(updateWebSocketHandler(), "/updateWebSocketHandler").addInterceptors(webSocketHandshakeInterceptor());
        webSocketHandlerRegistry.addHandler(updateWebSocketHandler(), "/updateWebSocketHandler/socketJs").addInterceptors(webSocketHandshakeInterceptor()).withSockJS();
        logger.info("registry updateWebSocketHandler");
    }

    @Bean
    public WebSocketHandler updateWebSocketHandler() {
        return new UpdateWebSocketHandler();
    }

    @Bean
    public WebSocketHandshakeInterceptor webSocketHandshakeInterceptor() {
        return new WebSocketHandshakeInterceptor();
    }
}
