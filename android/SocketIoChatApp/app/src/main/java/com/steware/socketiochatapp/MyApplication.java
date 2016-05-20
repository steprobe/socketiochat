package com.steware.socketiochatapp;

import android.app.Application;
import android.app.Service;

import com.steware.socketiochatapp.di.SocketIOModule;

import javax.inject.Singleton;

import dagger.Component;

public class MyApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Singleton
    @Component(modules = { SocketIOModule.class })
    public interface ApplicationComponent {
        void inject(Service service);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerMyApplication_ApplicationComponent.builder()
                .socketIOModule(new SocketIOModule())
                .build();
    }

    public ApplicationComponent component() {
        return mApplicationComponent;
    }
}
