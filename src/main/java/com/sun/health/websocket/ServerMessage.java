package com.sun.health.websocket;

/**
 * Created by 华硕 on 2018-06-12.
 */
public class ServerMessage {

    private String responseMessage;

    public ServerMessage() {
    }

    public ServerMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
