package com.sun.health.websocket;

import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * Created by 华硕 on 2018-06-12.
 */
//@Controller
public class WebSocketController {


    @MessageMapping("send")
    @SendTo("/topic/subscribe")
    public ServerMessage send(ClientMessage clientMessage) {
        System.out.println(clientMessage.getName());
        return new ServerMessage("返回" + clientMessage.getName());
    }

    @MessageMapping("get")
    @SendTo("/topic")
    public ServerMessage get() {
        System.out.println("GET");
        return new ServerMessage("返回GET");
    }

    @SubscribeMapping("subscribe")
    public ServerMessage sub() {
        System.out.println("订阅");
        return new ServerMessage("感谢");
    }




}
