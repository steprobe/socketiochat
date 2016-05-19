package com.steware.socketiochatapp.di;

import com.steware.socketiochatapp.service.WebSocketServiceProxy;

public interface DependencyProvider {
    WebSocketServiceProxy createWebSocketServiceProxy();
}
