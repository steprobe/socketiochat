package com.steware.socketiochatapp;

import android.app.Application;
import android.support.v4.app.FragmentActivity;

import javax.inject.Singleton;

import dagger.Component;

public class MyApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Singleton
    @Component(modules = { AndroidModule.class, CommonModule.class })
    public interface ApplicationComponent {
        void inject(FragmentActivity mainActivity);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerMyApplication_ApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .commonModule(new CommonModule())
                .build();
    }

    public ApplicationComponent component() {
        return mApplicationComponent;
    }
}
