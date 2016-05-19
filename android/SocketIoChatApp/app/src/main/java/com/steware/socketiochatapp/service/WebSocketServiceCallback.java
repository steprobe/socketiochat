package com.steware.socketiochatapp.service;

public interface WebSocketServiceCallback {

    void onIsTypingMapUpdated(Object isTypingEvents);

    void onNewTypingEventReceived(String str, String str2, String str3, Integer num, Boolean bool);

    void onNewWebSocketMessageReceived(String str);

    void onUserUpdated(String str);

    void onWebSocketConnected();

    void onWebSocketConnectionFailed();

    void onWebSocketDisconnected();
}