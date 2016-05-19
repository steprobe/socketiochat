package com.steware.socketiochatapp.service;

import org.json.JSONArray;

public interface SocketIO {

    interface Callback {
        void onConnectCompleted();
        void onConnectionFailed();
        void onEvent(JSONArray argument);
    }

    void connect(String uri, final Callback callback);
    void emit(String name, JSONArray args);
    void on(String event);
}
