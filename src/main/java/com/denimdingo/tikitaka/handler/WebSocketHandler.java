package com.denimdingo.tikitaka.handler;

import com.denimdingo.tikitaka.dto.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        var sessionId = session.getId();
        sessions.put(sessionId, session); // 1) 세션 저장

        Message message = Message.builder()
                .sender(sessionId)
                .receiver("all")
                .build();

        message.newConnect();
        logger.info("(info) payload : {}", message);

        sessions.values().forEach(s -> { // 2) 모든 세션에 알림
            try {
                if(!s.getId().equals(sessionId)) {
                    s.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
                }
            }
            catch (Exception e) {
                logger.info("(info) Error Occurred ", e);
            }
        });
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        Message message = objectMapper.readValue(textMessage.getPayload(), Message.class);
        message.setSender(session.getId());
        logger.info("(info) check get Message {}", message);
        sessions.values().forEach(s -> { // 2) 모든 세션에 알림
            try {
                if(s != null && s.isOpen()) {
                    s.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
                }
            }
            catch (Exception e) {
                logger.info("(info) Error Occurred ", e);
            }
        });
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @NonNull CloseStatus status) {
        var sessionId = session.getId();

        sessions.remove(sessionId);

        final Message message = new Message();
        message.closeConnect();
        message.setSender(sessionId);

        sessions.values().forEach(s-> {
            try {
                s.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
                logger.info("(info) status {}", status);
            } catch (Exception e) {
                logger.info("(info) Error Occurred {}", e.toString());
            }
        });
    }

}
