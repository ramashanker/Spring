package com.health.care.app.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Observable;
import java.util.Observer;

public class WebSocketObjectObserver implements Observer {

    private SimpMessagingTemplate webSocket;
    private String destination;

    public WebSocketObjectObserver(SimpMessagingTemplate webSocket, String destination) {
        this.webSocket = webSocket;
        this.destination = destination;
    }

    @Override
    public void update(Observable observable, Object object) {
        this.webSocket.convertAndSend(destination, object);
    }
}
