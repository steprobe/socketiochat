package com.steware.socketiochatapp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CommonModule {

    @Provides @Singleton
    NumberFactory providesNumberFactory() {
        return new NumberFactory();
    }

}
