package com.steware.socketiochatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class TestActivity extends AppCompatActivity implements DependencyProvider {

    public static DependencyProvider dependencyProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_layout);
        Log.e("STEO", "Creating activity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("STEO", "Resuming activity");
    }

    @Override
    public WebSocketServiceProxy createWebSocketServiceProxy() {
        return dependencyProvider.createWebSocketServiceProxy();
    }
}
