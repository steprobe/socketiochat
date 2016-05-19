package com.steware.socketiochatapp.service;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.Acknowledge;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.EventCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;

import org.json.JSONArray;

public class SocketIOImpl implements SocketIO {

    private SocketIOClient mSocketIOClient;
    private Callback mCallback;

    @Override
    public void connect(String uri, Callback callback) {

        AsyncHttpClient httpClient = AsyncHttpClient.getDefaultInstance();
        mCallback = callback;
        SocketIOClient.connect(httpClient, uri, new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, SocketIOClient client) {
                if(ex != null) {
                    ex.printStackTrace();
                    mCallback.onConnectionFailed();
                    return;
                }

                mSocketIOClient = client;
                mCallback.onConnectCompleted();
            }
        });
    }

    @Override
    public void emit(String name, JSONArray args) {

        if(mSocketIOClient == null) {
            return;
        }

        mSocketIOClient.emit(name, args);
    }

    @Override
    public void on(String event) {

        if(mSocketIOClient == null || mCallback == null) {
            return;
        }

        mSocketIOClient.on(event, new EventCallback() {
            @Override
            public void onEvent(JSONArray argument, Acknowledge acknowledge) {

                if(mCallback == null) {
                    return;
                }

                mCallback.onEvent(argument);
            }
        });
    }
}
