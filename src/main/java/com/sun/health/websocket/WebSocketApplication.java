package com.sun.health.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by 华硕 on 2018-06-12.
 */
@Controller
@EnableScheduling
@SpringBootApplication
public class WebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketApplication.class);
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/")
    public ServerMessage link(ClientMessage clientMessage, HttpRequest request, HttpSession session) {
        System.out.println(clientMessage.getName());
        return new ServerMessage("返回 " +clientMessage.getName());
    }

    @MessageMapping("/send")
    @SendTo("/topic/send")
    public ServerMessage send(ClientMessage clientMessage, HttpRequest request, HttpSession session) {
        System.out.println(clientMessage.getName());
        return new ServerMessage("返回 " +clientMessage.getName());
    }

//    @Scheduled(fixedRate = 1000)
//    @SendTo("/topic/callback")
    public Object callback() throws Exception {
        messagingTemplate.convertAndSend("/topic/callback", new Date());
        return "callback";
    }

    private static final String SESSION_USER = "user";

    @Autowired
    private CustomWebSocketHandler customWebSocketHandler;

    @RequestMapping("/login")
    @ResponseBody
    public String login(UserBean userBean, HttpServletRequest request) {
        request.getSession().setAttribute(SESSION_USER, userBean);
        return "success";
    }

}
