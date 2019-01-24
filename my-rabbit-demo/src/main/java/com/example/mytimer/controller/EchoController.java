package com.example.mytimer.controller;

import com.example.mytimer.mq.Receiver;
import com.example.mytimer.mq.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EchoController.class);

    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;


    @GetMapping("/v1/say-hai")
    public String sayHai(String q) {

        LOGGER.info("receive q ==> {}", q);
        this.sender.send(q);
        return "hai "+ q;
    }
}
