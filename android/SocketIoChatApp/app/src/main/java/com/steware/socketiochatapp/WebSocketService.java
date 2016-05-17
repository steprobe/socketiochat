package com.steware.socketiochatapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.Acknowledge;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.EventCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class WebSocketService extends Service {

    private static final String SOCKET_IO_SERVER = "http://10.8.1.118:3000";
    private static final String NEW_MESSAGE = "new-message";
    private static final String SEND_MESSAGE = "send-message";

    private WebSocketBinder mBinder = new WebSocketBinder();
    private final List<WebSocketServiceCallback> mCallbacks = new ArrayList<>();
    private SocketIOClient mSocketClient;

    private Handler mHandler = new Handler();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        connectToSocky();
        return mBinder;
    }

    public void addWebSocketServiceCallback(WebSocketServiceCallback webSocketServiceCallback) {
        if (!mCallbacks.contains(webSocketServiceCallback)) {
            mCallbacks.add(webSocketServiceCallback);
        }
    }

    public void removeWebSocketServiceCallback(WebSocketServiceCallback webSocketServiceCallback) {
        mCallbacks.remove(webSocketServiceCallback);
    }

    public void sendMessage(String message) {
        if (mSocketClient != null) {
            JSONArray array = new JSONArray();
            array.put(message);
            mSocketClient.emit(SEND_MESSAGE, array);
        }
    }

    public class WebSocketBinder extends Binder {
        WebSocketService getService() {
            return WebSocketService.this;
        }
    }

    private void connectToSocky() {

        AsyncHttpClient client = AsyncHttpClient.getDefaultInstance();
        SocketIOClient.connect(client, SOCKET_IO_SERVER, new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, SocketIOClient client) {

                if (ex != null) {
                    ex.printStackTrace();
                    notifyFailed();
                    return;
                }

                mSocketClient = client;
                notifyConnected();

                client.on(NEW_MESSAGE, new EventCallback() {
                    @Override
                    public void onEvent(final JSONArray argument, Acknowledge acknowledge) {
                        postNewMessage(argument);
                    }
                });
            }
        });
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
