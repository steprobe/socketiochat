package com.steware.socketiochatapp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements DependencyProvider {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment fraggle = MainFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fraggle);
        transaction.commit();
    }

    @Override
    public WebSocketServiceProxy createWebSocketServiceProxy(WebSocketServiceCallback callback) {
        return new WebSocketServiceProxy(this, callback);
    }
}
