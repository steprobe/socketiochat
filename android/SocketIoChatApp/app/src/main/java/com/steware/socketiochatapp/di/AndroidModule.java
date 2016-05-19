package com.steware.socketiochatapp.di;

import com.steware.socketiochatapp.MyApplication;
import com.steware.socketiochatapp.StringFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {

    private final MyApplication mApplication;

    public AndroidModule(MyApplication application) {
        mApplication = application;
    }

    @Provides @Singleton
    StringFactory providesStringFactory() {
        return new StringFactory(mApplication);
    }
}
