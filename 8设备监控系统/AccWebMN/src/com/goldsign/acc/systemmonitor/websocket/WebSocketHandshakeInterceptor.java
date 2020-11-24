package com.goldsign.acc.systemmonitor.websocket;

import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Description:
 *
 * @author: zhongziqi
 * @Date: 2018-07-04
 * @Time: 11:57
 */
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        // 解决The extension [x-webkit-deflate-frame] is not supported问题
        if (serverHttpRequest.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
            serverHttpRequest.getHeaders().set("Sec-WebSocket-Extensions",
                    "permessage-deflate");
        }
        System.out.println("Before Handshake");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("afterHandshake");
    }
}
