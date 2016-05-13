package com.steware.socketiochatapp;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MyModule {

    @Provides @Singleton
    WebSocketServiceProxy provideWebServiceProxy(Context context) {
        return new WebSocketServiceProxy(context);
    }
}
