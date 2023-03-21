package com.denimdingo.tikitaka.controller;

import com.denimdingo.tikitaka.dto.Message;
import com.denimdingo.tikitaka.dto.Response;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ChatController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Response send(Message message) throws Exception
    {
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new Response(message.getFrom(), message.getText(), time);
    }
}
