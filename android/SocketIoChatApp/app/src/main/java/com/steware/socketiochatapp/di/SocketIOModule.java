package com.steware.socketiochatapp.di;

import com.steware.socketiochatapp.service.SocketIO;
import com.steware.socketiochatapp.service.SocketIOImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SocketIOModule {

    @Provides @Singleton
    SocketIO providesSocketIO() {
        return new SocketIOImpl();
    }

}
