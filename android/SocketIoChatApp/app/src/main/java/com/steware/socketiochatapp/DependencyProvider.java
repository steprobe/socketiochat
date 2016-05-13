package com.steware.socketiochatapp;

public interface DependencyProvider {
    WebSocketServiceProxy createWebSocketServiceProxy(WebSocketServiceCallback callback);
}
