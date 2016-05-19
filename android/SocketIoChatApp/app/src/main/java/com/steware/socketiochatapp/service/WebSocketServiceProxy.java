package com.steware.socketiochatapp.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class WebSocketServiceProxy implements ServiceConnection {

    private boolean mIsBound;
    private Context mContext;
    private WebSocketServiceCallback mCallback;
    private WebSocketService mService;

    public WebSocketServiceProxy(Context context) {
        mContext = context;
    }

    public void bind(WebSocketServiceCallback callback) {

        mCallback = callback;
        Intent svcIntent = new Intent(mContext, WebSocketService.class);
        mContext.bindService(svcIntent, this, Context.BIND_AUTO_CREATE);
    }

    public void unbind() {
        if (mIsBound) {
            mContext.unbindService(this);
            mService.removeWebSocketServiceCallback(mCallback);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {

        mService = ((WebSocketService.WebSocketBinder) binder).getService();
        mService.addWebSocketServiceCallback(mCallback);

        mIsBound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mIsBound = false;
    }

    public void sendMessage(String message) {
        mService.sendMessage(message);
    }
}
