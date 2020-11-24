package com.goldsign.acc.systemmonitor.websocket;

import com.goldsign.acc.frame.controller.MenuController;
import com.goldsign.acc.frame.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * Description:
 *
 * @author: zhongziqi
 * @Date: 2018-07-04
 * @Time: 11:22
 */
public class UpdateWebSocketHandler extends TextWebSocketHandler {
    private Logger logger = Logger.getLogger(UpdateWebSocketHandler.class);
    public static CopyOnWriteArraySet<WebSocketSession> users = new CopyOnWriteArraySet<WebSocketSession>();

    /**
     * 接受客户端发送信息、处理
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if ("update".equals(message.getPayload())) {
            MenuController menuController = SpringContextUtil.getBean("menuController");
            List<Map> list = menuController.getMenuStatus();
            String jsonResult = menuController.getMeunStatuJson(list);
            sendMessageToUser(session.getId(), new TextMessage(jsonResult));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        users.add(session);
        logger.info("连接成功，目前websocket连接数==" + users.size());
//        sendMessageToAllUsers(new TextMessage("update"));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        users.remove(session);
        logger.info("异常关闭，目前websocket连接数==" + users.size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        users.remove(session);
        logger.info("关闭，目前websocket连接数==" + users.size());
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToAllUsers(TextMessage message) {
        logger.info("通知更新==" + message.toString());
        logger.info("目前websocket连接数==" + users.size());
        for (WebSocketSession user : users) {
            logger.info("AllUsers userId" + user.getId());
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param sessionId
     * @param message
     */
    public void sendMessageToUser(String sessionId, TextMessage message) {
        for (WebSocketSession user : users) {
            logger.info("User userId" + user.getId());
            if (user.getId().equals(sessionId)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

}
