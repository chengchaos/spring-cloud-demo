package com.example.mytimer.controller;


import com.example.mytimer.entity.RequestMessage;
import com.example.mytimer.entity.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import scala.util.parsing.json.JSON;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class BroadcastController {

    public static final Logger LOGGER = LoggerFactory.getLogger(BroadcastController.class);

    // 收到消息记数
    private AtomicInteger count = new AtomicInteger(0);

    /**
     * {@code @MessageMapping }: 指定要接收消息的地址,类似 RequestMapping
     * {@code @SendTo} : 默认消息将被发送到与传入消息相同的目的地
     * @param requestMessage
     * @return
     */
    @MessageMapping("/receive")
    @SendTo("/topic/messages")
    public ResponseMessage broadcast(RequestMessage requestMessage) {

        LOGGER.info("receive message = {}", requestMessage);

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setId("2");
        requestMessage.setName("BroadcastCtl receive [" + count.incrementAndGet() + "] records");
        return responseMessage;

    }

    @RequestMapping(value="/broadcast/index")
    public String broadcastIndex(HttpServletRequest request) {

        LOGGER.info("host : {}", request.getRemoteHost());

        return "websocket/simple/ws-broadcast";
    }
}
