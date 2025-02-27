package com.example.socketapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@RestController
public class WebSocketController {

    private final MyWebSocketHandler webSocketHandler;

    // Inject MyWebSocketHandler via constructor
    public WebSocketController(MyWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        for (WebSocketSession session : webSocketHandler.getSessions()) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage("Server Broadcast: " + message));
                } catch (IOException e) {
                    return "Failed to send message to session " + session.getId();
                }
            }
        }
        return "Message sent to all WebSocket clients!";
    }
}