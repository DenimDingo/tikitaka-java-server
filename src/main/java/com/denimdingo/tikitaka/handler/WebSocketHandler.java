package com.denimdingo.tikitaka.handler;

import com.denimdingo.tikitaka.dto.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        var sessionId = session.getId();
        sessions.put(sessionId, session); // 1) 세션 저장

        Message message = Message.builder()
                .sender(sessionId)
                .receiver("all")
                .build();

        message.newConnect();

        sessions.values().forEach(s -> { // 2) 모든 세션에 알림
            try {
                if(!s.getId().equals(sessionId)) {
                    TextMessage tm = new TextMessage(message.toString());
                    s.sendMessage(tm);
                    log.info("payload : {}", tm.getPayload());
                    log.debug("send message!");
                    log.info("(info) send message!");
                }
            }
            catch (Exception e) {
                // TODO: throw
                log.error("An Exception Occurred", e);
            }
        });
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        Message message = objectMapper.readValue(textMessage.getPayload(), Message.class);
        message.setSender(session.getId());
        WebSocketSession receiver = sessions.get(message.getReceiver());

        if (receiver != null && receiver.isOpen()) {
            receiver.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

}
