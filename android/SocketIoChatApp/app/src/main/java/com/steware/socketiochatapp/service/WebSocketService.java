package com.steware.socketiochatapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class WebSocketService extends Service implements SocketIO.Callback {

    private static final String SOCKET_IO_SERVER = "http://10.8.1.118:3000";
    private static final String NEW_MESSAGE = "new-message";
    private static final String SEND_MESSAGE = "send-message";

    private WebSocketBinder mBinder = new WebSocketBinder();
    private final List<WebSocketServiceCallback> mCallbacks = new ArrayList<>();
    private SocketIO mSocketClient = new SocketIOImpl();

    private Handler mHandler = new Handler();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mSocketClient.connect(SOCKET_IO_SERVER, this);
        return mBinder;
    }

    public void addWebSocketServiceCallback(WebSocketServiceCallback webSocketServiceCallback) {
        if (!mCallbacks.contains(webSocketServiceCallback)) {
            mCallbacks.add(webSocketServiceCallback);
        }
    }

    public void removeWebSocketServiceCallback(WebSocketServiceCallback webSocketServiceCallback) {
        mCallbacks.remove(webSocketServiceCallback);
        if(mCallbacks.isEmpty()) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    public void sendMessage(String message) {
        JSONArray array = new JSONArray();
        array.put(message);
        mSocketClient.emit(SEND_MESSAGE, array);
    }

    @Override
    public void onConnectCompleted() {
        notifyConnected();
        mSocketClient.on(NEW_MESSAGE);
    }

    @Override
    public void onConnectionFailed() {
        notifyFailed();
    }

    @Override
    public void onEvent(JSONArray argument) {
        postNewMessage(argument);
    }

    public class WebSocketBinder extends Binder {
        WebSocketService getService() {
            return WebSocketService.this;
        }
    }

    private void postNewMessage(final JSONArray newMessageJson) {

        try {
            final String newMessage = newMessageJson.getString(0);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    for (WebSocketServiceCallback callback : mCallbacks) {
                        callback.onNewWebSocketMessageReceived(newMessage);
                    }
                }
            });
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void notifyConnected() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                for (WebSocketServiceCallback callback : mCallbacks) {
                    callback.onWebSocketConnected();
                }
            }
        });
    }

    private void notifyFailed() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                for (WebSocketServiceCallback callback : mCallbacks) {
                    callback.onWebSocketConnectionFailed();
                }
            }
        });
    }
}
