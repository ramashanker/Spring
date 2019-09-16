package com.state.machine.spring.app.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate webSocket;

    private static SimpMessagingTemplate staticWebSocket;

    private ScheduledExecutorService sendLogRowsScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> sendLogRowsTimer;
    private List<String> logRowsToSend = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        staticWebSocket = this.webSocket;
    }

    public void sendLogRow(String row) {
        logRowsToSend.add(row);

        if (sendLogRowsTimer != null) {
            sendLogRowsTimer.cancel(false);
        }

        sendLogRowsTimer = sendLogRowsScheduledExecutor.schedule(() -> {
            webSocket.convertAndSend("/topic/log-rows", logRowsToSend);
            logRowsToSend.clear();
        }, 100, TimeUnit.MILLISECONDS);
    }

    public static void watchObservable(Observable observable, String destination) {
        observable.addObserver(new WebSocketObjectObserver(staticWebSocket, destination));
    }

    public static void sendObject(Object object, String destination) {
        staticWebSocket.convertAndSend(destination, object);
    }
}
