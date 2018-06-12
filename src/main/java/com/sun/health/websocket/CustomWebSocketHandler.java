package com.sun.health.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 华硕 on 2018-06-12.
 */
@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private static final Map<Integer, WebSocketSession> users = new HashMap<Integer, WebSocketSession>();

    private static final String SESSION_USER = "user";

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        users.remove(getUserId(session));
        session.sendMessage(new TextMessage("欢迎"));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()) {
            session.close();
        }
        System.out.println("连接出错");
        users.remove(getUserId(session));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("成功连接");
        Integer userId = getUserId(session);
        System.out.println(userId);
        if(userId != null) {
            users.put(userId, session);
            session.sendMessage(new TextMessage("成功建立socket连接"));
            System.out.println(userId);
            System.out.println(session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(getUserId(session) + " 发送" + message.toString());
        session.sendMessage(new TextMessage("返回" + message));
    }

    public boolean sendMessageToUser(Integer clientId, String message) {
        if(users.get(clientId) == null) {
            return false;
        }
        WebSocketSession session = users.get(clientId);
        System.out.println("sendMessage " + session);
        if(!session.isOpen()) {
            return false;
        }
        TextMessage textMessage = new TextMessage(message);
        try {
            session.sendMessage(textMessage);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendMessageToAllUsers(String message) {
        boolean isAllOK = true;
        TextMessage textMessage = new TextMessage(message);
        for (WebSocketSession session : users.values()) {
            try {
                if(!session.isOpen()) {
                    isAllOK = false;
                    break;
                }
                session.sendMessage(textMessage);
            } catch (IOException e) {
                e.printStackTrace();
                isAllOK = false;
            }
        }
        return isAllOK;
    }

    private Integer getUserId(WebSocketSession session) {
        Object obj = session.getAttributes().get(SESSION_USER);
        if(obj instanceof UserBean) {
            UserBean userBean = (UserBean) obj;
            return userBean.getId();
        }
        return null;
    }
}
